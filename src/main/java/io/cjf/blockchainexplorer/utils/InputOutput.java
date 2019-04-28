package io.cjf.blockchainexplorer.utils;



import io.cjf.blockchainexplorer.api.BitcoinJsonRpcClient;
import io.cjf.blockchainexplorer.dto.TxDetailInTxInfoDTO;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.cjf.blockchainexplorer.enumeration.TransactionDetailType;
import org.springframework.stereotype.Repository;




@Repository
public class InputOutput {

    public TxDetailInTxInfoDTO importVoutDetail(JSONObject vout){
        TxDetailInTxInfoDTO txDetailTxInfo = new TxDetailInTxInfoDTO();
        JSONObject scriptPubKey = vout.getJSONObject("scriptPubKey");
        JSONArray addresses = scriptPubKey.getJSONArray("addresses");
        if (addresses!=null && !addresses.isEmpty()){
            txDetailTxInfo.setAddress(addresses.getString(0));
        }
        Double value = vout.getDouble("value");
        txDetailTxInfo.setAmount(value);

        txDetailTxInfo.setType((byte) TransactionDetailType.Receive.ordinal());
        return txDetailTxInfo;
    }

    public TxDetailInTxInfoDTO importVinDetail(JSONObject vin, BitcoinJsonRpcClient bitcoinJsonRpcClient) throws Throwable {
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

        txDetailTxInfo.setAmount(amount);
        JSONObject scriptPubKey = jsonObject.getJSONObject("scriptPubKey");
        JSONArray addresses = scriptPubKey.getJSONArray("addresses");
        if (addresses!=null && !addresses.isEmpty()){
            txDetailTxInfo.setAddress(addresses.getString(0));
        }

        return txDetailTxInfo;
    }
}
