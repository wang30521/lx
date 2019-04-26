package io.cjf.blockchainexplorer.dao;

import io.cjf.blockchainexplorer.po.TransactionDetail;
import io.cjf.blockchainexplorer.po.TransactionDetailKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TransactionDetailMapper {
    int deleteByPrimaryKey(TransactionDetailKey key);

    int truncate();

    int insert(TransactionDetail record);

    int insertSelective(TransactionDetail record);

    TransactionDetail selectByPrimaryKey(TransactionDetailKey key);

    List<TransactionDetail> selectByAddress(@Param("address") String address);

    int updateByPrimaryKeySelective(TransactionDetail record);

    int updateByPrimaryKey(TransactionDetail record);
}