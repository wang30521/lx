package io.cjf.blockchainexplorer.dto;

import java.util.List;

public class TransactionTotalDTO {
    //未被确认的交易数
    private Integer count;

    private Double feetTotal;

    private Double sizeTotal;

    private Double transactionPerSecond;

    private List<TransactionInBlockDTO> txList;

    public List<TransactionInBlockDTO> getTxList() {
        return txList;
    }

    public void setTxList(List<TransactionInBlockDTO> txList) {
        this.txList = txList;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Double getFeetTotal() {
        return feetTotal;
    }

    public void setFeetTotal(Double feetTotal) {
        this.feetTotal = feetTotal;
    }

    public Double getSizeTotal() {
        return sizeTotal;
    }

    public void setSizeTotal(Double sizeTotal) {
        this.sizeTotal = sizeTotal;
    }

    public Double getTransactionPerSecond() {
        return transactionPerSecond;
    }

    public void setTransactionPerSecond(Double transactionPerSecond) {
        this.transactionPerSecond = transactionPerSecond;
    }
}
