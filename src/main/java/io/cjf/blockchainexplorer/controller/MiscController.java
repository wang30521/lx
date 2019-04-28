package io.cjf.blockchainexplorer.controller;

import io.cjf.blockchainexplorer.dto.ImportStateDTO;
import io.cjf.blockchainexplorer.service.MiscService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/misc")
@EnableAutoConfiguration
public class MiscController {

    @Autowired
    private MiscService miscService;

    //根据关键字查询
    @GetMapping("/search")
    public Object search(@RequestParam String keyword){
        return null;
    }

    //同步数据 第一步 全部导入 根据Height
    @GetMapping("/importFromHeight")
    public void importFromHeight(@RequestParam Integer blockHeight,
                                 @RequestParam(required = false, defaultValue = "false") Boolean isClean){
        miscService.importFromHeight(blockHeight, isClean);
    }
    //同步数据 第一步 全部导入 根据Hash
    @GetMapping("/importFromHash")
    public void importFromHash(@RequestParam String blockhash,
                               @RequestParam(required = false, defaultValue = "false") Boolean isClean) throws Throwable {
        miscService.importFromHash(blockhash, isClean);
    }
    //查看当前导入的状态 由于导入的数据较多 要查看
    @GetMapping("/getImportState")
    public ImportStateDTO getImportState(){
        return null;
    }
}
