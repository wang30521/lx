package io.cjf.blockchainexplorer.dao;

import io.cjf.blockchainexplorer.po.Transaction;

public interface TransactionMapper {
    int deleteByPrimaryKey(String txid);

    int truncate();

    int insert(Transaction record);

    int insertSelective(Transaction record);

    Transaction selectByPrimaryKey(String txid);

    int updateByPrimaryKeySelective(Transaction record);

    int updateByPrimaryKey(Transaction record);
}