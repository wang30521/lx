package io.cjf.blockchainexplorer.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;
import java.util.HashMap;

@Service
public class BitcoinJsonRpcClient {

    private JsonRpcHttpClient jsonRpcHttpClient;

    public BitcoinJsonRpcClient() throws MalformedURLException {
        HashMap<String, String> headers = new HashMap<>();
        String authStrOrig = String.format("%s:%s","root","root");
        String authStr = Base64.getEncoder().encodeToString(authStrOrig.getBytes());
        String authStrResult = String.format("Basic %s",authStr);
        headers.put("Authorization",authStrResult);
        jsonRpcHttpClient = new JsonRpcHttpClient(new URL("http://localhost:18332"),headers);
    }

    public String getBlockHashByHeight(Integer blockHeight) throws Throwable {
        String blockhash = jsonRpcHttpClient.invoke("getblockhash", new Integer[]{blockHeight}, String.class);
        return blockhash;
    }

    public Double getBalance(String address) throws Throwable {
        JSONArray balances = jsonRpcHttpClient.invoke("listunspent", new Object[]{6, 9999999, new String[]{address}}, JSONArray.class);
        JSONObject balance = balances.getJSONObject(0);
        Double amount = balance.getDouble("amount");
        return amount;
    }

    public JSONObject getRawTransaxtion(String txid) throws Throwable {
        JSONObject rawTransaction = jsonRpcHttpClient.invoke("getrawtransaction", new Object[]{txid, true}, JSONObject.class);
        return rawTransaction;
    }

    public String getBestBlockhash() throws Throwable {
        String bestblockhash = jsonRpcHttpClient.invoke("getbestblockhash", new Object[]{}, String.class);
        return bestblockhash;
    }

    //获取交易详细信息 hash
    public JSONObject getmempoolentry(String txid) throws Throwable {
        JSONObject getrawmempool = jsonRpcHttpClient.invoke("getmempoolentry", new Object[]{txid}, JSONObject.class);
        return getrawmempool;
    }
    //获取交易池的信息
    public JSONArray getrawmempool() throws Throwable {
        JSONArray getrawmempool = jsonRpcHttpClient.invoke("getrawmempool", new Object[]{false}, JSONArray.class);
        return getrawmempool;
    }

    //获取交易池的详细信息
    public JSONArray getMempoolInfo() throws Throwable {

        JSONArray jsonArray = new JSONArray();

        JSONArray getrawmempool = getrawmempool();

        for (int i = 0; i < getrawmempool.size(); i++){

            String txid = getrawmempool.getString(i);

            JSONObject getmempoolentry = getmempoolentry(txid);
            getmempoolentry.put("hash", txid);

            jsonArray.add(getmempoolentry);
        }

        return jsonArray;
    }
}
