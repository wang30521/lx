package io.cjf.blockchainexplorer.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.cjf.blockchainexplorer.api.BitcoinApi;
import io.cjf.blockchainexplorer.api.BitcoinJsonRpcClient;
import io.cjf.blockchainexplorer.dao.BlockMapper;
import io.cjf.blockchainexplorer.dao.TransactionDetailMapper;
import io.cjf.blockchainexplorer.dao.TransactionMapper;
import io.cjf.blockchainexplorer.enumeration.TransactionDetailType;
import io.cjf.blockchainexplorer.po.Block;
import io.cjf.blockchainexplorer.po.Transaction;
import io.cjf.blockchainexplorer.po.TransactionDetail;
import io.cjf.blockchainexplorer.service.MiscService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MiscServiceImpl implements MiscService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BitcoinApi bitcoinApi;

    @Autowired
    private BitcoinJsonRpcClient bitcoinJsonRpcClient;

    @Autowired
    private BlockMapper blockMapper;

    @Autowired
    private TransactionMapper transactionMapper;

    @Autowired
    private TransactionDetailMapper transactionDetailMapper;

    @Async
    @Override
    public void importByHeight(Integer blockHeight, Boolean isClean) {

    }

    @Async
    @Override
    public void importByHash(String blockHash, Boolean isClean) throws Throwable {

        String tempHash = blockHash;

        //如果tomcat或者mysql崩溃了或者上次数据并没有全部导入 如何续存
        //先查询Block中有没有数据  没有数据则是续存
        //查询Block的条数 大于0则表示有数据 需要续存
//        int count = blockMapper.selectCount();
//        if (count > 0) {
//            Integer maxHeight = blockMapper.selectMaxHeight();
//            maxHeight += 2;
//            String blockHashByHeight = bitcoinJsonRpcClient.getBlockHashByHeight(maxHeight);
//            tempHash = blockHashByHeight;
//        }

        while (tempHash != null && !tempHash.isEmpty()){
            JSONObject blockOrigin = bitcoinApi.getBlock(tempHash);
            Block block = new Block();
            block.setBlockhash(blockOrigin.getString("hash"));
            block.setBlockchainId(2);
            block.setHeight(blockOrigin.getInteger("height"));
            Long time = blockOrigin.getLong("time");
            Date date = new Date(time * 1000);
            block.setTime(date);
            //交易信息集
            JSONArray txs = blockOrigin.getJSONArray("tx");
            for (int i = 0; i < txs.size(); i++) {
                JSONObject jsonObject = txs.getJSONObject(i);
                importTx(jsonObject, tempHash, date);
            }
            block.setTxSize(txs.size());
            block.setSizeOnDisk(blockOrigin.getLong("size"));
            block.setDifficulty(blockOrigin.getDouble("difficulty"));
            block.setPrevBlockhash(blockOrigin.getString("previousblockhash"));
            block.setNextBlockhash(blockOrigin.getString("nextblockhash"));
            block.setMerkleRoot(blockOrigin.getString("merkleroot"));
            blockMapper.insert(block);
            tempHash = blockOrigin.getString("nextblockhash");
            System.out.println("块的下标: "  + block.getHeight());
        }
        System.out.println("完成！！！");
    }

    public void importTx(JSONObject tx, String blockHash, Date time) throws Throwable {

        Transaction transaction = new Transaction();
        String txid = tx.getString("txid");
        transaction.setTxid(txid);
        transaction.setTxhash(tx.getString("hash"));
        transaction.setBlockhash(blockHash);
        transaction.setSize(tx.getLong("size"));
        transaction.setWeight(tx.getInteger("weight"));
        transaction.setTime(time);
        transactionMapper.insert(transaction);

        JSONArray vouts = tx.getJSONArray("vout");
        for (int i = 0; i < vouts.size(); i++) {
            importVoutDetail(vouts.getJSONObject(i),txid);
        }

        JSONArray vins = tx.getJSONArray("vin");

        //todo vin0 coinbase tx

        for (int i = 1; i < vins.size(); i++) {
            importVinDetail(vins.getJSONObject(i),txid);
        }
    }

    public void importVoutDetail(JSONObject vout, String txid){
        TransactionDetail transactionDetail = new TransactionDetail();
        transactionDetail.setTxid(txid);
        JSONObject scriptPubKey = vout.getJSONObject("scriptPubKey");
        JSONArray addresses = scriptPubKey.getJSONArray("addresses");
        //todo check whether sync addresses
        if (addresses != null && !addresses.isEmpty()){
            String address = addresses.getString(0);
            transactionDetail.setAddress(address);
        }
        Double amount = vout.getDouble("value");
        transactionDetail.setAmount(amount);
        transactionDetail.setType((byte) TransactionDetailType.Receive.ordinal());

        transactionDetailMapper.insert(transactionDetail);

    }

    public void importVinDetail(JSONObject vin, String txidOrigin) throws Throwable {
        String txid = vin.getString("txid");
        Integer vout = vin.getInteger("vout");

        JSONObject rawTransaxtion = bitcoinJsonRpcClient.getRawTransaxtion(txid);
        JSONArray vouts = rawTransaxtion.getJSONArray("vout");
        JSONObject jsonObject = vouts.getJSONObject(vout);

        TransactionDetail transactionDetail = new TransactionDetail();
        transactionDetail.setTxid(txidOrigin);
        transactionDetail.setType((byte) TransactionDetailType.Send.ordinal());
        Double amount = jsonObject.getDouble("value");
        transactionDetail.setAmount(amount);
        JSONObject scriptPubKey = jsonObject.getJSONObject("scriptPubKey");
        JSONArray addresses = scriptPubKey.getJSONArray("addresses");
        if (addresses != null){
            String address = addresses.getString(0);
            transactionDetail.setAddress(address);
        }

        transactionDetailMapper.insert(transactionDetail);
    }
}
