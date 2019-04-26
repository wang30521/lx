package io.cjf.blockchainexplorer.service.impl;

import io.cjf.blockchainexplorer.dao.BlockMapper;
import io.cjf.blockchainexplorer.po.Block;
import io.cjf.blockchainexplorer.service.BlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlockServiceImpl implements BlockService {

    @Autowired
    private BlockMapper blockMapper;

    @Override
    public List<Block> selectRecent() {
        List<Block> blocks = blockMapper.selectRecent();
        return blocks;
    }

    @Override
    public Block getBlockDetail(String blockhash) {
        Block block = blockMapper.selectByPrimaryKey(blockhash);
        return block;
    }
}
