package com.wdl.handler.controller;

import com.wdl.handler.constant.BusinessResultCode;
import com.wdl.handler.util.IOUtil;
import com.wdl.handler.vo.ResultVO;
import com.wdl.handler.vo.Results;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Slf4j
@RestController
@RequestMapping("/handler")
public class HandlerController {
    //文件保存统一根目录
    public static final String FILE_ROOT_DIRECTORY = "D://springbootRootFilePath/";
    //全局计数器
    private static int count = 1;

    @RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
    public ResultVO fileUpload(MultipartFile file) throws IOException {
        log.info("============>>>>文件开始上传!");
        if (file == null || file.isEmpty()) {
            return Results.businessError(BusinessResultCode.FILE_EMPTY);
        }
        InputStream is = file.getInputStream();
        String fileName = file.getOriginalFilename();
        int fileNameLength = fileName.length();
        String fileType = fileName.substring(fileName.lastIndexOf("."), fileNameLength);
        if (fileType == null) {
            return Results.businessError(BusinessResultCode.FILE_TYPE_INCORRECT);
        }
        //文件命名
        File newFile = new File(FILE_ROOT_DIRECTORY);
        if(!newFile.exists()){
            newFile.mkdir();
        }
        String copyfileName = FILE_ROOT_DIRECTORY+(System.currentTimeMillis()) + "_" + (count++) + "_" + fileName;
        log.info("=============>>>>文件上传路径:{}",copyfileName);
        OutputStream os = new FileOutputStream(new File(copyfileName));
        //复制文件
        int  i= IOUtil.copy(is, os);
        log.info("============>>>>文件上传成功!文件大小：{}",i);
        return Results.success();
    }

    /**
     * 获取全局计数器
     *
     * @return
     */
    private synchronized int getCount() {
        int i = count++;
        return i;
    }
}
