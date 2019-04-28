package io.cjf.blockchainexplorer.service.impl;

import io.cjf.blockchainexplorer.api.BitcoinApi;
import io.cjf.blockchainexplorer.api.BitcoinJsonRpcClient;
import io.cjf.blockchainexplorer.controller.ReceiveInTxInAddressDTO;
import io.cjf.blockchainexplorer.controller.TransactionInAddressDTO;
import io.cjf.blockchainexplorer.dao.TransactionDetailMapper;
import io.cjf.blockchainexplorer.dto.AddressInfo;
import io.cjf.blockchainexplorer.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private TransactionDetailMapper transactionDetailMapper;

    @Autowired
    private BitcoinApi bitcoinAPI;

    @Autowired
    private BitcoinJsonRpcClient bitcoinJsonRpcClient;


    /**
     * 根据address查询该地址的所有交易信息
     * 查询方式: 通过数据库同步的信息的查询
     * @param address
     * @return
     */
    @Override
    public AddressInfo getAddressInfo(String address) {

        Double inputTotal = 0.0;
        Double outputTotal = 0.0;

        //该交易地址下的所有交易信息
        List<TransactionInAddressDTO> list = transactionDetailMapper.getTransactionDetailByAddress(address);

        //错误代码  贼蠢
//        for (TransactionInAddressDTO ta : list){
//
//            //查出 当地址一致时, 且是发送者的时候的txid
//            List<TransactionInAddressDTO> txIdList = transactionDetailMapper.getSenderTxId(address, ta.getType());
//            for (TransactionInAddressDTO t : txIdList){
//                String txId = t.getTxId();
//                List<ReceiveInTxInAddressDTO> receivedList = transactionDetailMapper.getReceived(txId);
//                ta.setReceiveList(receivedList);
//            }
//        }

        //遍历list 把type=1的信息取出来  就是当角色为send时
        List<TransactionInAddressDTO> senderTxId = transactionDetailMapper.getSenderTxId(address);
        for (TransactionInAddressDTO t : senderTxId){
            String txId = t.getTxId();
            List<ReceiveInTxInAddressDTO> receivedList = transactionDetailMapper.getReceived(txId);
            for (TransactionInAddressDTO ta : list){
                String taTxId = ta.getTxId();
                if (taTxId.equals(txId))
                    ta.setReceiveList(receivedList);
            }

        }

        for (TransactionInAddressDTO t : list){

            if (t.getType() == 1)
                inputTotal += t.getAmount();

            if (t.getType() == 2)
                outputTotal += t.getAmount();

        }

        AddressInfo addressInfoDTO = new AddressInfo();
        addressInfoDTO.setList(list);
        addressInfoDTO.setAddress(address);
        addressInfoDTO.setTxSize(list.size());
        addressInfoDTO.setReceiveAmount(outputTotal);
        addressInfoDTO.setFinalBalance(outputTotal - inputTotal);

        return addressInfoDTO;
    }
}
