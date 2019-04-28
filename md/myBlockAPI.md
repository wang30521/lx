#Block API

信息描述  

| 方法名      | 方法类型        | 传入参数  | 返回类型  | 方法描述 |  
| ------------- |:-------------:|: -----:|  : -----:|  : -----:|  
| 例:getList()      | GET/POST | ..... |  ..... |  方法的作用 |  
 ### 1 getRecentBlocksById()
 ```json 

 #### 方法类型: Get   
 #### 传入参数: blockchainId 区块链的自增id 
 #### 返回类型: List<BlockListDTO>  BlockListDTO集 
 #### 方法描述: 根据区块链的id查询块的信息集List<BlockListDTO>
 
###getRecentBlocksById详细信息: 
axios.get('http://localhost:8080/block/getRecentBlocksById', {  
    params: {  
      blockchainId: blockchainId  
    }  
  })  
.then(function (response) {
    console.log(response);
  })
  .catch(function (error) {
    console.log(error);
  });
  
```

界面显示:    

| Height/高度      | time/块的出生时间        | Transactions/总交易数  | blockSizeOnDisk/该块目前所占的容量  |  
| ------------- |:-------------:|: -----:|  : -----:|   
| 571886      | 15 minutes  | 2047 |  1,168,370 |   
| 571885      | 25 minutes  | 1235 |  1,063,996 |   
| 571884      | 32 minutes  | 2530 |  1,220,361 | 


### 2 getRecentBlocksByNameType()

 ```json 

#### 方法类型: Get   
#### 传入参数: bcName 区块链的bcName和 bcType区块链的bcType 联合唯一索引(区块链的名字和类型)
#### 返回类型: List<BlockListDTO>  BlockListDTO集 
#### 方法描述: 根据区块链的name和type查询块的信息集List<BlockListDTO>

### getRecentBlocksByNameType详细信息: 
axios.get('http://localhost:8080/block/getRecentBlocksByNameType', {  
    params: {  
      bcName: bcName,
      bcType: bcType
    }  
  })  
.then(function (response) {
    console.log(response);
  })
  .catch(function (error) {
    console.log(error);
  });
  
```

界面显示:    

| Height/高度      | time/块的出生时间        | Transactions/总交易数  | blockSizeOnDisk/该块目前所占的容量  |  
| ------------- |:-------------:|: -----:|  : -----:|   
| 571886      | 15 minutes  | 2047 |  1,168,370 |   
| 571885      | 25 minutes  | 1235 |  1,063,996 |   
| 571884      | 32 minutes  | 2530 |  1,220,361 | 

### 3 getBlockDetailByHash()

 ```json 

#### 方法类型: Get   
#### 传入参数: blockHash 块Block的blockHash值 (块hash值)
#### 返回类型: BlockDetailDTO  该dto有属性txList 包含该块下的所有交易信息
#### 方法描述: 根据Block的blockHash值查询Block的信息(BlockDetailDTO)和该Block下的所有交易信息(txList)

### getBlockDetailByHash详细信息: 
axios.get('http://localhost:8080/block/getBlockDetailByHash', {  
    params: {  
      blockHash: blockHash
    }  
  })  
.then(function (response) {
    console.log(response);
  })
  .catch(function (error) {
    console.log(error);
  });
  
```

###界面显示(显示块信息和交易信息):    

####Block      #571886   

|  属性    | 值        |
| :-------------: |:-------------:| 
|    blockHash    | 0000000000000000001abd08df8c64174bb09584f00f035fca8694511c5374a0 |  
|    blockHeight    | 571886 (Main Chain) | 
|    blockTime    | 2019-04-16 13:19:44  |

####Transactions

|    44f37d20143dc68a5be475268589c8ed85d0c6a51e9a8b46a744eb2dc3d77aae      |  | 2019-04-16 13:19:44 |
| :-------------: |:-------------:| :-------------:| 
|    发钱    | 收钱  |  金额  |  
| 1N3e5yUk3ywWFN2jUMAx4UF8cPfYb6Yv2Y  | 1N3e5yUk3ywWFN2jUMAx4UF8cPfYb6Yv2Y  | 0.00090228BTC  |

|    06b558c90a6fd854306503e93a41111208ef6815ab16b6fba1d8d0ee70526b2a      |  | 2019-04-16 13:10:43 |
| :-------------: |:-------------:| :-------------:| 
|    发钱    | 收钱  |  金额  |  
| 15uuy33L6Uy3zWCHAcv4rFf4maQntkvQ9E  | 	1Gr1to27RbFA5NGyxw29Mcmgt8DRjaVhcs <br> 175mpHDwNb6cp9wrYZmw5rnxuqwqYthkWi   | 0.12862821 BTC <br> 0.00099454 BTC  |


|    9450a4b7092cdc46437dc21e7c5b07467e354891ebdf443934ba967904abcfcc      |  | 2019-04-16 13:17:44 |
| :-------------: |:-------------:| :-------------:| 
|    发钱    | 收钱  |  金额  |  
| 3DFHucRYFiyVK31hYp1enh1Fa9X51RM8Qx <br> 3QNXNAAdsJLcE8pPuvFgJL5QpYfcyPi8y7 <br> 36f5o3JYuYYpEzRRfXomEoL2Ma9H2xzNay | 3G1mnoEWbpn5gEH5o7dAqju3cQs1RD85j4 <br> 3NJ6GKoqQ5WwrhWctpic9jQctmRC5xGUJH <br> 3K7go7hk9Uez7agvxnEwRy1hEZcCyh4q5b   | 0.00477981 BTC <br> 0.00935576 BTC <br> 0.98782964 BTC |


### 4 getBlockDetailByHeight()

 ```json 

#### 方法类型: Get   
#### 传入参数: blockHeight 块Block的blockHeight值(块高度值)
#### 返回类型: BlockDetailDTO  该dto有属性txList 包含该块下的所有交易信息
#### 方法描述: 根据Block的blockHeight值查询Block的信息(BlockDetailDTO)和该Block下的所有交易信息(txList)

### getBlockDetailByHash详细信息: 
axios.get('http://localhost:8080/block/getBlockDetailByHash', {  
    params: {  
      blockHeight: blockHeight
    }  
  })  
.then(function (response) {
    console.log(response);
  })
  .catch(function (error) {
    console.log(error);
  });
  
```

###界面显示(显示块信息和交易信息):    

####Block      #571885  

|  属性    | 值        |
| :-------------: |:-------------:| 
|    blockHash    | 0000000000000000001abd08df8c64174bb09584f00f035fca8694511c5374a0 |  
|    blockHeight    | 571885 (Main Chain) | 
|    blockTime    | 2019-04-16 13:19:44  |

####Transactions

|    44f37d20143dc68a5be475268589c8ed85d0c6a51e9a8b46a744eb2dc3d77aae      |  | 2019-04-16 13:19:44 |
| :-------------: |:-------------:| :-------------:| 
|    发钱    | 收钱  |  金额  |  
| 1N3e5yUk3ywWFN2jUMAx4UF8cPfYb6Yv2Y  | 1N3e5yUk3ywWFN2jUMAx4UF8cPfYb6Yv2Y  | 0.00090228BTC  |

|    06b558c90a6fd854306503e93a41111208ef6815ab16b6fba1d8d0ee70526b2a      |  | 2019-04-16 13:10:43 |
| :-------------: |:-------------:| :-------------:| 
|    发钱    | 收钱  |  金额  |  
| 15uuy33L6Uy3zWCHAcv4rFf4maQntkvQ9E  | 	1Gr1to27RbFA5NGyxw29Mcmgt8DRjaVhcs <br> 175mpHDwNb6cp9wrYZmw5rnxuqwqYthkWi   | 0.12862821 BTC <br> 0.00099454 BTC  |


|    9450a4b7092cdc46437dc21e7c5b07467e354891ebdf443934ba967904abcfcc      |  | 2019-04-16 13:17:44 |
| :-------------: |:-------------:| :-------------:| 
|    发钱    | 收钱  |  金额  |  
| 3DFHucRYFiyVK31hYp1enh1Fa9X51RM8Qx <br> 3QNXNAAdsJLcE8pPuvFgJL5QpYfcyPi8y7 <br> 36f5o3JYuYYpEzRRfXomEoL2Ma9H2xzNay | 3G1mnoEWbpn5gEH5o7dAqju3cQs1RD85j4 <br> 3NJ6GKoqQ5WwrhWctpic9jQctmRC5xGUJH <br> 3K7go7hk9Uez7agvxnEwRy1hEZcCyh4q5b   | 0.00477981 BTC <br> 0.00935576 BTC <br> 0.98782964 BTC |
