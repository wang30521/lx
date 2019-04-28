package io.cjf.blockchainexplorer.controller;

import io.cjf.blockchainexplorer.dao.TransactionDetailMapper;
import io.cjf.blockchainexplorer.dto.AddressInfo;
import io.cjf.blockchainexplorer.dto.TransactionInBlockDTO;
import io.cjf.blockchainexplorer.po.TransactionDetail;
import io.cjf.blockchainexplorer.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
@EnableAutoConfiguration
@CrossOrigin
public class AddressController {
    @Autowired
    private AddressService addressService;

    @Autowired
    private TransactionDetailMapper transactionDetailMapper;

    @GetMapping("/getAddressInfo")
    public AddressInfo getAddressInfo(@RequestParam String address){
        AddressInfo addressInfoDTO = addressService.getAddressInfo(address);

        return addressInfoDTO;
    }

    @GetMapping("/getAddressTransactions")
    public List<TransactionDetail> getAddressTransactions(@RequestParam String address,
                                                              @RequestParam(required = false, defaultValue = "1") Integer pageNum){
        List<TransactionDetail> transactionDetails = transactionDetailMapper.selectByAddress(address);
        return transactionDetails;
    }

}
