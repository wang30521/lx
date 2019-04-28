package io.cjf.blockchainexplorer.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class TransactionInBlockDTO implements Serializable {
    private String txid;

    private String txhash;

    private Long size;

    private Date time;

    private Double oneTxOutputTotal;

    private List<TxDetailInTxInfoDTO> txDetailTxInfo;

    public Double getOneTxOutputTotal() {
        return oneTxOutputTotal;
    }

    public void setOneTxOutputTotal(Double oneTxOutputTotal) {
        this.oneTxOutputTotal = oneTxOutputTotal;
    }

    public List<TxDetailInTxInfoDTO> getTxDetailTxInfo() {
        return txDetailTxInfo;
    }

    public void setTxDetailTxInfo(List<TxDetailInTxInfoDTO> txDetailTxInfo) {
        this.txDetailTxInfo = txDetailTxInfo;
    }

    public String getTxid() {
        return txid;
    }

    public void setTxid(String txid) {
        this.txid = txid;
    }

    public String getTxhash() {
        return txhash;
    }

    public void setTxhash(String txhash) {
        this.txhash = txhash;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}


