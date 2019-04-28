package io.cjf.blockchainexplorer.dto;

import java.io.Serializable;
import java.util.List;

public class AddressInfo implements Serializable {
    private String address;
    private String hash160;
    private Integer txSize;
    private Double receiveAmount;
    private Double finalBalance;
    private List<TransactionInAddressDTO> list;

    public List<TransactionInAddressDTO> getList() {
        return list;
    }

    public void setList(List<TransactionInAddressDTO> list) {
        this.list = list;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHash160() {
        return hash160;
    }

    public void setHash160(String hash160) {
        this.hash160 = hash160;
    }

    public Integer getTxSize() {
        return txSize;
    }

    public void setTxSize(Integer txSize) {
        this.txSize = txSize;
    }

    public Double getReceiveAmount() {
        return receiveAmount;
    }

    public void setReceiveAmount(Double receiveAmount) {
        this.receiveAmount = receiveAmount;
    }

    public Double getFinalBalance() {
        return finalBalance;
    }

    public void setFinalBalance(Double finalBalance) {
        this.finalBalance = finalBalance;
    }
}
