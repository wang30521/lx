package io.cjf.blockchainexplorer.service;

import io.cjf.blockchainexplorer.dto.BlockDetailDTO;
import io.cjf.blockchainexplorer.po.Block;

import java.util.List;

public interface BlockService {
    List<Block> selectRecent();

    Block getBlockDetail(String blockhash);

    BlockDetailDTO getBlockDetail(Integer blockHeight) throws Throwable;
}
