package io.cjf.blockchainexplorer.dao;

import feign.Param;
import io.cjf.blockchainexplorer.dto.BlockViewMoreDTO;
import io.cjf.blockchainexplorer.po.Block;

import java.util.List;

public interface BlockMapper {
    int deleteByPrimaryKey(String blockhash);

    int truncate();

    int insert(Block record);

    int insertSelective(Block record);

    Block selectByPrimaryKey(String blockhash);

    List<Block> selectRecent();

    int updateByPrimaryKeySelective(Block record);

    int updateByPrimaryKey(Block record);

    int selectCount();

    int selectMaxHeight();

    Block selectByHeight(@Param("maxHeight") Integer maxHeight);

    List<BlockViewMoreDTO> viewMore(@Param("newTime")String newTime);
}