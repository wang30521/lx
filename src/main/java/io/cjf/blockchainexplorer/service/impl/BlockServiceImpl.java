package io.cjf.blockchainexplorer.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.cjf.blockchainexplorer.api.BitcoinApi;
import io.cjf.blockchainexplorer.api.BitcoinJsonRpcClient;
import io.cjf.blockchainexplorer.dao.BlockMapper;
import io.cjf.blockchainexplorer.dto.BlockDetailDTO;
import io.cjf.blockchainexplorer.dto.TransactionInBlockDTO;
import io.cjf.blockchainexplorer.dto.TxDetailInTxInfoDTO;
import io.cjf.blockchainexplorer.enumeration.TransactionDetailType;
import io.cjf.blockchainexplorer.po.Block;
import io.cjf.blockchainexplorer.service.BlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BlockServiceImpl implements BlockService {

    @Autowired
    private BlockMapper blockMapper;

    @Autowired
    private BitcoinApi bitcoinAPI;

    @Autowired
    private BitcoinJsonRpcClient bitcoinJsonRpcClient;

    //比特币 总的接收数量
    private double outputTotal=0;

    //比特币 总的发送数量
    private double inputTotal = 0;

    //比特币 每次交易的总接收值
    private double oneTxOutputTotal = 0;

    @Override
    public BlockDetailDTO getBlockDetail(Integer blockHeight) throws Throwable {

        //通过height 获取块的信息
        String blockHash = bitcoinJsonRpcClient.getBlockHashByHeight(blockHeight);
        JSONObject block = bitcoinAPI.getBlock(blockHash);

        //块信息的DTO 里面有交易信息  用于页面显示
        BlockDetailDTO blockDetailDto = new BlockDetailDTO();

        blockDetailDto.setBlockhash(block.getString("hash"));
        blockDetailDto.setHeight(block.getInteger("height"));

        blockDetailDto.setMerkleRoot(block.getString("merkleroot"));
        blockDetailDto.setDifficulty(block.getDouble("difficulty"));
        blockDetailDto.setNextBlockhash(block.getString("nextblockhash"));
        blockDetailDto.setPrevBlockhash(block.getString("previousblockhash"));
        blockDetailDto.setSizeOnDisk(block.getLong("size"));
        JSONArray txs = block.getJSONArray("tx");
        List<TransactionInBlockDTO> tranList=new ArrayList<>();
        for (int i = 0; i < txs.size(); i++) {
            TransactionInBlockDTO transactionInBlockDto= importTx(txs.getJSONObject(i),new Date(block.getLong("time")*1000));
            tranList.add(transactionInBlockDto);
            if(i == 0){
                Double jiangli = transactionInBlockDto.getOneTxOutputTotal();
                blockDetailDto.setJiangli(jiangli);
            }
        }
        blockDetailDto.setTxList(tranList);
        blockDetailDto.setTxSize(txs.size());
        Long time = Long.valueOf(block.getInteger("time"));
        time *= 1000;
        System.out.println(time);
        blockDetailDto.setBlockTime(new Date(time));
        blockDetailDto.setTime(time);
        blockDetailDto.setOutputTotal(outputTotal);
        blockDetailDto.setTransactionFees(inputTotal);

        outputTotal = 0;
        inputTotal = 0;

        return blockDetailDto;
    }


    public TransactionInBlockDTO importTx(JSONObject tx, Date time) throws Throwable {
        TransactionInBlockDTO transactionInBlockDto = new TransactionInBlockDTO();
        String txid = tx.getString("txid");
        transactionInBlockDto.setTxid(txid);
        transactionInBlockDto.setTxhash(tx.getString("hash"));
        transactionInBlockDto.setTime(time);
        transactionInBlockDto.setSize(tx.getLong("size"));

        JSONArray vouts = tx.getJSONArray("vout");
        List<TxDetailInTxInfoDTO> txDetailTxInfos=new ArrayList<>();
        for (int i=0;i<vouts.size();i++){
            TxDetailInTxInfoDTO txDetailTxInfo = importVoutDetail(vouts.getJSONObject(i));
            oneTxOutputTotal += txDetailTxInfo.getAmount();
            txDetailTxInfos.add(txDetailTxInfo);
        }
        transactionInBlockDto.setOneTxOutputTotal(oneTxOutputTotal);
        oneTxOutputTotal = 0;

        JSONArray vins = tx.getJSONArray("vin");
        for (int i = 0; i < vins.size(); i++) {
            TxDetailInTxInfoDTO txDetailTxInfo = importVinDetail(vins.getJSONObject(i));
            txDetailTxInfos.add(txDetailTxInfo);
        }

        transactionInBlockDto.setTxDetailTxInfo(txDetailTxInfos);

        return transactionInBlockDto;

    }

    public TxDetailInTxInfoDTO importVoutDetail(JSONObject vout){
        TxDetailInTxInfoDTO txDetailTxInfo = new TxDetailInTxInfoDTO();
        JSONObject scriptPubKey = vout.getJSONObject("scriptPubKey");
        JSONArray addresses = scriptPubKey.getJSONArray("addresses");
        if (addresses!=null && !addresses.isEmpty()){
            txDetailTxInfo.setAddress(addresses.getString(0));
        }
        Double value = vout.getDouble("value");
        txDetailTxInfo.setAmount(value);
        outputTotal += value;
        txDetailTxInfo.setType((byte) TransactionDetailType.Receive.ordinal());
        return txDetailTxInfo;
    }

    public TxDetailInTxInfoDTO importVinDetail(JSONObject vin) throws Throwable {
        String txid = vin.getString("txid");
        Integer vout=vin.getInteger("vout");
        TxDetailInTxInfoDTO txDetailTxInfo = new TxDetailInTxInfoDTO();
        if (txid==null){
            return txDetailTxInfo;
        }
        JSONObject rawTransaxtion = bitcoinJsonRpcClient.getRawTransaxtion(txid);
        JSONArray vouts = rawTransaxtion.getJSONArray("vout");
        JSONObject jsonObject = vouts.getJSONObject(vout);

        txDetailTxInfo.setType((byte) TransactionDetailType.Send.ordinal());
        Double amount = jsonObject.getDouble("value");
        inputTotal += amount;
        txDetailTxInfo.setAmount(amount);
        JSONObject scriptPubKey = jsonObject.getJSONObject("scriptPubKey");
        JSONArray addresses = scriptPubKey.getJSONArray("addresses");
        if (addresses!=null && !addresses.isEmpty()){
            txDetailTxInfo.setAddress(addresses.getString(0));
        }

        return txDetailTxInfo;
    }
}
