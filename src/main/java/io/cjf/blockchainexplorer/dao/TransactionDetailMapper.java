package io.cjf.blockchainexplorer.dao;

import io.cjf.blockchainexplorer.controller.ReceiveInTxInAddressDTO;
import io.cjf.blockchainexplorer.dto.TransactionInAddressDTO;
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

    List<TransactionInAddressDTO> getTransactionDetailByAddress(@Param("address") String address);

    List<TransactionInAddressDTO> getSenderTxId(@Param("address") String address);

    List<ReceiveInTxInAddressDTO> getReceived(@Param("txid") String txid);
}