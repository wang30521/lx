package io.cjf.blockchainexplorer.dto;

import java.util.Date;

public class BlockListDTO {
    private String blockhash;
    private Integer height;
    private Date time;
    private Integer txSize;
    private Long sizeOnDisk;

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getTxSize() {
        return txSize;
    }

    public void setTxSize(Integer txSize) {
        this.txSize = txSize;
    }

    public Long getSizeOnDisk() {
        return sizeOnDisk;
    }

    public void setSizeOnDisk(Long sizeOnDisk) {
        this.sizeOnDisk = sizeOnDisk;
    }

    public void setTime(Date time) {
        this.time = time;
    }


    public String getBlockhash() {
        return blockhash;
    }

    public void setBlockhash(String blockhash) {
        this.blockhash = blockhash;
    }
}
