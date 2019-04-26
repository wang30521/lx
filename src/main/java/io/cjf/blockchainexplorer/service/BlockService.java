package io.cjf.blockchainexplorer.service;

import io.cjf.blockchainexplorer.po.Block;

import java.util.List;

public interface BlockService {
    List<Block> selectRecent();

    Block getBlockDetail(String blockhash);
}
