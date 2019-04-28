package io.cjf.blockchainexplorer.controller;

import java.util.Date;
import java.util.List;

public class TransactionInAddressDTO {
    private String txId;
    private Date txTime;
    private Double amount;
    private Integer type;
    //当该交易信息的type为1时, 也就是为发送者时, 页面需要展示接收者信息
    private List<ReceiveInTxInAddressDTO> receiveList;

    public List<ReceiveInTxInAddressDTO> getReceiveList() {
        return receiveList;
    }

    public void setReceiveList(List<ReceiveInTxInAddressDTO> receiveList) {
        this.receiveList = receiveList;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTxId() {
        return txId;
    }

    public void setTxId(String txId) {
        this.txId = txId;
    }


    public Date getTxTime() {
        return txTime;
    }

    public void setTxTime(Date txTime) {
        this.txTime = txTime;
    }
}
