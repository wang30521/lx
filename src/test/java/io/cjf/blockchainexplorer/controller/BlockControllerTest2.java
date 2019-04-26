package io.cjf.blockchainexplorer.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import io.cjf.blockchainexplorer.dto.BlockListDTO;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class BlockControllerTest2 {


    @Test
    public void getRecentBlocks() throws Throwable {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://localhost:8080/block/getRecentBlocks")
                .build();
        Response response = client.newCall(request).execute();
        String string = response.body().string();
        JSONArray jsonArray = JSON.parseArray(string);
        assertEquals(5,jsonArray.size());
    }
}