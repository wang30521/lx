package io.cjf.blockchainexplorer.dto;

import io.cjf.blockchainexplorer.po.TransactionDetail;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class TransactionInfoDTO implements Serializable {
    private String txid;

    private String txhash;

    private Long size;

    private Integer weight;

    private Date time;

    private Double totalInput;

    private Double totalOutput;

    private Double fees;

    private List<TxDetailInTxInfoDTO> txDetails;

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

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Double getTotalInput() {
        return totalInput;
    }

    public void setTotalInput(Double totalInput) {
        this.totalInput = totalInput;
    }

    public Double getTotalOutput() {
        return totalOutput;
    }

    public void setTotalOutput(Double totalOutput) {
        this.totalOutput = totalOutput;
    }

    public Double getFees() {
        return fees;
    }

    public void setFees(Double fees) {
        this.fees = fees;
    }

    public List<TxDetailInTxInfoDTO> getTxDetails() {
        return txDetails;
    }

    public void setTxDetails(List<TxDetailInTxInfoDTO> txDetails) {
        this.txDetails = txDetails;
    }
}
