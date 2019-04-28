package io.cjf.blockchainexplorer.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BlockScheduler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SimpMessageSendingOperations messageSendingOperations;

    //十分钟更新一次数据 针对Block的生成和推送
    @Async
    @Scheduled(fixedRate = 2000000)
    public void importBlockTransaction(){

        logger.info("start import block");
        messageSendingOperations.convertAndSend("/topic", "/block/getRecentBlocksById");
    }

    //十分钟更新一次数据 针对Transaction的生成和推送
    @Async
    @Scheduled(fixedRate = 1000000)
    public void importTransaction(){

        logger.info("start import transactions");
        messageSendingOperations.convertAndSend("/aaa", "/transaction/getTranscations");
    }

}
