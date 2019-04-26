package io.cjf.blockchainexplorer.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.cjf.blockchainexplorer.api.BitcoinApi;
import io.cjf.blockchainexplorer.api.BitcoinJsonRpcClient;
import io.cjf.blockchainexplorer.dao.BlockMapper;
import io.cjf.blockchainexplorer.dto.BlockDetailDTO;
import io.cjf.blockchainexplorer.dto.BlockListDTO;
import io.cjf.blockchainexplorer.po.Block;
import io.cjf.blockchainexplorer.service.BlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/block")
@CrossOrigin
public class BlockController {

    @Autowired
    private BitcoinApi bitcoinApi;

    @Autowired
    private BitcoinJsonRpcClient bitcoinJsonRpcClient;

    @Autowired
    private BlockMapper blockMapper;

    @Autowired
    private BlockService blockService;

    @Value("${blockchain.recentCount}")
    private Integer recentCount;

    @GetMapping("/getRecentBlocks")
    public List<BlockListDTO> getRecentBlocks() throws Throwable {

       String bestBlockhash = bitcoinJsonRpcClient.getBestBlockhash();
        String tempBlockhash = bestBlockhash;

       List<BlockListDTO> blockListDTOS = new LinkedList<>();

        for (int i = 0; i < 5; i++) {
            JSONObject block = bitcoinApi.getNoTxBlock(tempBlockhash);
            BlockListDTO blockListDTO = new BlockListDTO();
            blockListDTO.setHeight(block.getInteger("height"));
            Long time = block.getLong("time");
            Date date = new Date(time * 1000);
           blockListDTO.setTime(date);
            blockListDTO.setTxSize(block.getJSONArray("tx").size());
            blockListDTO.setSizeOnDisk(block.getLong("size"));
            blockListDTOS.add(blockListDTO);
            tempBlockhash = block.getString("previousblockhash");
        }

       /*JSONObject chainInfo = bitcoinApi.getChainInfo();
       Integer height = chainInfo.getInteger("blocks");
        height -= 5;
       String blockHashByHeight = bitcoinJsonRpcClient.getBlockHashByHeight(height);
       JSONArray blockHeaders = bitcoinApi.getBlockHeaders(5, blockHashByHeight);

        LinkedList<BlockListDTO> blockListDTOS = new LinkedList<>();
        for (int i = 4; i > -1; i--) {
            JSONObject block = blockHeaders.getJSONObject(i);
            BlockListDTO blockListDTO = new BlockListDTO();
            blockListDTO.setHeight(block.getInteger("height"));
           Long time = block.getLong("time");
           Date date = new Date(time * 1000);
           blockListDTO.setTime(date);
           //todo add size on disk
           blockListDTO.setTxSize(block.getInteger("nTx"));
           blockListDTOS.add(blockListDTO);
       }*/

//        List<Block> blocks = blockMapper.selectRecent();
        /*List<Block> blocks = blockService.selectRecent();
        List<BlockListDTO> blockListDTOS = blocks.stream().map(block -> {
            BlockListDTO blockListDTO = new BlockListDTO();
            blockListDTO.setBlockhash(block.getBlockhash());
            blockListDTO.setHeight(block.getHeight());
            blockListDTO.setTime(block.getTime().getTime());
            blockListDTO.setTxSize(block.getTxSize());
            blockListDTO.setSizeOnDisk(block.getSizeOnDisk());
            return blockListDTO;
        }).collect(Collectors.toList());*/

        return blockListDTOS;


//        bitcoinApi.getBlockHeaders(recentCount,bestBlockhash)
    }

    @GetMapping("/getRecentBlocksByNameType")
    public List<BlockListDTO> getRecentBlocksByNameType(@RequestParam String name,
                                                        @RequestParam String type){
        return null;
    }

    @GetMapping("/getBlockDetailByHash")
    public BlockDetailDTO getBlockDetailByHash(@RequestParam String blockhash){
        Block block = blockService.getBlockDetail(blockhash);
        BlockDetailDTO blockDetailDTO = new BlockDetailDTO(block);
        return blockDetailDTO;
    }

    @GetMapping("/getBlockDetailByHeight")
    public BlockDetailDTO getBlockDetailByHeight(@RequestParam Integer blockheight){
        return null;
    }
}
