package io.cjf.blockchainexplorer.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.cjf.blockchainexplorer.api.BitcoinApi;
import io.cjf.blockchainexplorer.api.BitcoinJsonRpcClient;
import io.cjf.blockchainexplorer.dto.*;
import io.cjf.blockchainexplorer.utils.InputOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/transaction")
@EnableAutoConfiguration
@CrossOrigin
public class TransactionController {

    @Autowired
    private BitcoinApi bitcoinApi;

    @Autowired
    private BitcoinJsonRpcClient bitcoinJsonRpcClient;

    private Double outputTotal;

    private InputOutput inputOutput = new InputOutput();

    //查询交易信息列表
    @GetMapping("/getReccentTransactionById")
    public List<TransactionListDTO> getReccentTransaction(@RequestParam Integer blockchainId){

        return null;
    }

    //查询交易信息列表
    @GetMapping("/getReccentTransactionByNameType")
    public List<TransactionListDTO> getReccentTransaction(@RequestParam String name,
                                                          @RequestParam String type){

        return null;
    }

    //查询单个交易信息
    @GetMapping("/getTransactionInfoByTxId")
    public TransactionInfoDTO getTransactionInfoByTxid(@RequestParam String txid){

        return null;
    }

    //查询单个交易信息
    @GetMapping("/getTransactionInfoByTxhash")
    public TransactionInfoDTO getTransactionInfoByTxhash(@RequestParam String txhash){

        return null;
    }

    @GetMapping("/getTranscations")
    public List<TransactionListDTO> getTranscations() throws Throwable {

        List<TransactionListDTO> txs = new LinkedList<>();
        TransactionListDTO tx = null;
        Double amount = 0.0;

        JSONArray getrawmempool = bitcoinJsonRpcClient.getrawmempool();
        for(int i = 1; i < 6; i++){

            tx = new TransactionListDTO();

            String txid = getrawmempool.getString(getrawmempool.size() - i);
            tx.setTxid(txid);
            JSONObject getmempoolentry = bitcoinJsonRpcClient.getmempoolentry(txid);

            Long time = getmempoolentry.getLong("time");
            tx.setTime(time);
            JSONObject rawTransaxtion = bitcoinJsonRpcClient.getRawTransaxtion(txid);

            JSONArray vout = rawTransaxtion.getJSONArray("vout");
            for (int j = 0; j < vout.size(); j++){

                JSONObject jsonObject = vout.getJSONObject(j);
                Double value = jsonObject.getDouble("value");
                amount += value;
            }

            tx.setAmount(amount);
            amount = 0.0;
            txs.add(tx);
        }

        return txs;
    }

    @GetMapping("showTransactionViewMore")
    public TransactionTotalDTO showTransactionViewMore() throws Throwable {

//        JSONObject getrawmempool = bitcoinJsonRpcClient.getrawmempool();
//
//        JSONArray objects = new JSONArray(parse);
//        Object o = getrawmempool.get(1);
//        return o;

        TransactionTotalDTO transactionTotalDTO = new TransactionTotalDTO();
        Double fees = 0.0;
        Double sizes = 0.0;

        JSONArray mempoolInfo = bitcoinJsonRpcClient.getMempoolInfo();

        List<TransactionInBlockDTO> tibs = new ArrayList<>();
        for (int i = 0; i <mempoolInfo.size(); i++) {

            JSONObject jsonObject = mempoolInfo.getJSONObject(i);

            TransactionInBlockDTO transactionInBlockDTO = new TransactionInBlockDTO();
            transactionInBlockDTO.setOneTxOutputTotal(0.0);

            Double fee = jsonObject.getDouble("fee");
            fees += fee;

            Double size = jsonObject.getDouble("size");
            sizes += size;

            String txid = jsonObject.getString("hash");

            JSONObject rawTransaxtion = bitcoinJsonRpcClient.getRawTransaxtion(txid);

            JSONArray vouts = rawTransaxtion.getJSONArray("vout");

            List<TxDetailInTxInfoDTO> txDetailTxInfos=new ArrayList<>();
            for (int j=0;j<vouts.size();j++){
                JSONObject vout = vouts.getJSONObject(j);
                TxDetailInTxInfoDTO txDetailTxInfo = inputOutput.importVoutDetail(vout);
                transactionInBlockDTO.setOneTxOutputTotal(transactionInBlockDTO.getOneTxOutputTotal()+txDetailTxInfo.getAmount());
                txDetailTxInfos.add(txDetailTxInfo);
            }

//            transactionInBlockDTO.getOneTxOutputTotal()

            JSONArray vins = rawTransaxtion.getJSONArray("vin");
            for (int j = 0; j < vins.size(); j++) {
                TxDetailInTxInfoDTO txDetailTxInfo = inputOutput.importVinDetail(vins.getJSONObject(j), bitcoinJsonRpcClient);
                txDetailTxInfos.add(txDetailTxInfo);
            }

            transactionInBlockDTO.setTxDetailTxInfo(txDetailTxInfos);

            tibs.add(transactionInBlockDTO);

        }

        transactionTotalDTO.setTxList(tibs);
        transactionTotalDTO.setFeetTotal(fees);
        transactionTotalDTO.setSizeTotal(sizes);

        return transactionTotalDTO;
    }

}
