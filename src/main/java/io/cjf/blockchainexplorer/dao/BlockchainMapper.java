package io.cjf.blockchainexplorer.dao;

import io.cjf.blockchainexplorer.po.Blockchain;

public interface BlockchainMapper {
    int deleteByPrimaryKey(Integer blockchainId);

    int insert(Blockchain record);

    int insertSelective(Blockchain record);

    Blockchain selectByPrimaryKey(Integer blockchainId);

    int updateByPrimaryKeySelective(Blockchain record);

    int updateByPrimaryKey(Blockchain record);
}