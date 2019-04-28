package io.cjf.blockchainexplorer.service;

import io.cjf.blockchainexplorer.dto.AddressInfo;

public interface AddressService {
    AddressInfo getAddressInfo(String address);
}
