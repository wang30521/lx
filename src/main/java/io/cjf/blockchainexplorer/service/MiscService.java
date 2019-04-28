package io.cjf.blockchainexplorer.service;

public interface MiscService {

    //同步数据 第一步 全部导入 根据Height 判断isClean
    public void importByHeight(Integer blockHeight, Boolean isClean);

    //同步数据 第一步 全部导入 根据Hash
    void importByHash(String blockHash, Boolean isClean) throws Throwable;
}
