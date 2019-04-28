package io.cjf.blockchainexplorer.controller;


import io.cjf.blockchainexplorer.api.BitcoinApi;
import io.cjf.blockchainexplorer.api.BitcoinJsonRpcClient;
import io.cjf.blockchainexplorer.dao.BlockMapper;
import io.cjf.blockchainexplorer.dto.BlockDetailDTO;
import io.cjf.blockchainexplorer.dto.BlockListDTO;
import io.cjf.blockchainexplorer.dto.BlockViewMoreDTO;
import io.cjf.blockchainexplorer.po.Block;
import io.cjf.blockchainexplorer.service.BlockService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.bitcoinj.core.Address;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.script.Script;
import org.bitcoinj.script.ScriptBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.bitcoinj.script.ScriptOpCodes.*;

@RestController
@RequestMapping("/block")
@CrossOrigin
@EnableAutoConfiguration
public class BlockController {

    @Autowired
    private BitcoinApi bitcoinApi;

    @Autowired
    private BitcoinJsonRpcClient bitcoinJsonRpcClient;

    @Autowired
    private BlockMapper blockMapper;

    @Autowired
    private BlockService blockService;

    //存储开始时间和结束时间 第一次进入时间为0
    static Long start_day_time = 0L;
    static Long end_day_time = 0L;

   /* //bitcoin 生成hash160的方法之一
    public static Script createOutputScript(Address to) {
        if (to.isP2SHAddress()) {
            return new ScriptBuilder()
                    .op(OP_HASH160)
                    .data(to.getHash160())
                    .op(OP_EQUAL)
                    .build();
        } else {
            return new ScriptBuilder()
                    .op(OP_DUP)
                    .op(OP_HASH160)
                    .data(to.getHash160())
                    .op(OP_EQUALVERIFY)
                    .op(OP_CHECKSIG)
                    .build();
        }
    }*/

    @Value("${blockchain.recentCount}")
    private Integer recentCount;

    /**
     * addressToHash160
     * @return
     */
    /*@GetMapping("addressToHash160")
    public String addressToHash160(){

        NetworkParameters main = MainNetParams.get();
        Address address = new Address(main, "1Nh7uHdvY6fNwtQtM1G5EZAFPLC33B59rB");
        Script script =createOutputScript(address);
        System.out.println("hash 160:"+script.toString().split("\\[")[1].split("\\]")[0]);

        return script.toString().split("\\[")[1].split("\\]")[0];
    }*/


    //获取区块链的块的列表
    //首页的当前币的前五个块
    @GetMapping("/getRecentBlocks")
    public List<BlockListDTO> getRecentBlocks(@RequestParam(defaultValue = "2") Integer blockchainId) throws Throwable {

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

    /**
     * 区块列表 点击 view more的列表信息 显示一天的信息 显示当前天的所有块的信息
     *
     * @param isPage 判断是上一页还是下一页 上一页为 pre  下一页为false  默认值为"" 表示当天
     * @return
     * @throws Throwable
     */
    @GetMapping("/viewMore")
    public List<BlockViewMoreDTO> showBlocksViewMore(@RequestParam(defaultValue = "") String isPage,
                                                     @RequestParam(defaultValue = "") Long now)
            throws Throwable {

        String newTime=null;
        if (now.equals("")){
            newTime= LocalDate.now().toString();
        }else {
            Date date = new Date(now);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            newTime = simpleDateFormat.format(date);
        }

        List<BlockViewMoreDTO> viewMoreDTOS = blockMapper.viewMore(newTime);

        return viewMoreDTOS;

//        //一天的毫秒数
//        final Integer DAY_TIME = 24 * 60 * 60 * 1000;
//
//        //定义时间格式为 年月日
//        SimpleDateFormat formater = new SimpleDateFormat("yyyy/MM/dd");
//        //定义时间格式为 年月日时分秒
//        SimpleDateFormat formater2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//        //设置年月日的 '时分秒' 为 00:00:00 or 23:59:59 然后通过formater2转换成date
//
//        Date start = null;
//        Date end = null;
//        //判断时间是否为0  为0则表示第一次访问 第一次访问时间为当天 new Date()
//        //如果不为0则表示不是第一次访问 这里会把时间存入static修饰的变量 start_day_time 和 end_day_time 中 作为tomcat的变量 每次访问该方法都能获取
//        if (start_day_time != 0 & end_day_time != 0){
//
//            //开始的时间
//            start = new Date(start_day_time);
//            //结束的时间
//            end = (new Date(end_day_time));
//        }else{
//
//            //当天的开始时间
//            start = formater2.parse(formater.format(new Date()) + " 00:00:00");
//            //当天的结束时间
//            end = formater2.parse(formater.format(new Date())+ " 23:59:59");
//        }
//
//
//
//        long startTime = start.getTime();
//        long endTime = end.getTime();
//
//        //查询数据库里 当天的信息 between
//        //判断是上一天还是下一天还是当天
//        if (isPage.equals("pre")){
//
//            startTime -= DAY_TIME;
//            endTime -= DAY_TIME;
//        }else if(isPage.equals("next")){
//
//            startTime += DAY_TIME;
//            endTime += DAY_TIME;
//        }else{
//
//
//        }
//
//        start_day_time = startTime;
//        end_day_time = endTime;
//
//        List<BlockViewMoreDTO> viewMoreDTOS = blockMapper.viewMore(new Date(startTime), new Date(endTime));
//
//        return viewMoreDTOS;
    }

    //获取区块链的块的列表
    @GetMapping("/getRecentBlocksByNameType")
    public List<BlockListDTO> getRecentBlocksByNameType(@RequestParam String bcName,
                                                        @RequestParam String bcType){

        return null;
    }

    //获取块信息和块下的所有交易信息

    /**
     * 只是获取块信息和所有的交易信息
     * @param blockHash
     * @return
     */
    public BlockDetailDTO getBlockDetailByHash(@RequestParam String blockHash){



        return null;
    }

    //获取块信息和块下的所有交易信息

    /**
     * 获取块信息和所有的交易信息 分两种情况
     *
     * 1.首页的height值 在被点击后是 获取块信息和所有的交易信息
     * 2.点viewMore后再点击height后 获取块信息并不获取交易信息
     *
     * 先写第一种
     * @param blockHeight
     * @return
     */
    @GetMapping("/getBlockDetailByHeight")
    public BlockDetailDTO getBlockDetailByHeight(@RequestParam Integer blockHeight) throws Throwable {

        BlockDetailDTO block = blockService.getBlockDetail(blockHeight);

        return block;
    }

//    @MessageMapping("/getBlockFromRealTime")
//    @SendTo("/topic/block")
//    public BlockListDTO getBlockFromRealTime(String message) throws Throwable {
//
//        String bestBlockhash = bitcoinJsonRpcClient.getBestBlockhash();
//
//        JSONObject block = bitcoinApi.getNoTxBlock(bestBlockhash);
//        BlockListDTO blockListDTO = new BlockListDTO();
//        blockListDTO.setHeight(block.getInteger("height"));
//        Long time = block.getLong("time");
//        Date date = new Date(time * 1000);
//        blockListDTO.setAge(date);
//        blockListDTO.setTxSize(block.getJSONArray("tx").size());
//        blockListDTO.setSizeOnDisk(block.getLong("size"));
//
//        /// TODO: 2019/4/24 添加数据库
//
//        return blockListDTO;
//    }
}
