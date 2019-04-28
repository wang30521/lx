package io.cjf.blockchainexplorer.dto;

import java.util.Date;

public class BlockViewMoreDTO {
    private Integer height;
    private Date time;
    private String blockHash;
    private double sizeOnDisk;

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getBlockHash() {
        return blockHash;
    }

    public void setBlockHash(String blockHash) {
        this.blockHash = blockHash;
    }

    public double getSizeOnDisk() {
        return sizeOnDisk;
    }

    public void setSizeOnDisk(double sizeOnDisk) {
        this.sizeOnDisk = sizeOnDisk;
    }
}
