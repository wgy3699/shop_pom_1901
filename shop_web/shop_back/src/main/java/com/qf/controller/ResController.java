package com.qf.controller;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @version 1.0
 * @user lao王
 * @date 2019/5/20 21:00
 */
@Controller
@RequestMapping("/res")
public class ResController {

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @RequestMapping("/uploadImg")
    @ResponseBody
    public Map<String, String> uploadImg(MultipartFile file){

        //获得上传的图片的名称
        String filename = file.getOriginalFilename();

        //获得上传的图片大小
        long fileSize = file.getSize();

        //截取上传图片的后缀
        int indexOf = filename.indexOf(".");
        String substring = filename.substring(indexOf + 1);

        Map<String,String> map = new HashMap<>();

        try {
            StorePath storePath = fastFileStorageClient.uploadImageAndCrtThumbImage(
                    file.getInputStream(),
                    fileSize,
                    substring,
                    null
            );
            map.put("code","0000");
            map.put("filePath",storePath.getFullPath());
            return map;
        } catch (IOException e) {
            e.printStackTrace();
        }

        map.put("code","0001");

//        //获得上传的路径
//        String path = ResController.class.getResource("/static/file").getPath();
//
//        try (
//                //上传的文件流
//                InputStream ips = file.getInputStream();
//
//                //利用UUID区分上传的文件
//                OutputStream ops = new FileOutputStream(path+"/"+ UUID.randomUUID().toString()+substring);
//        ){
//            IOUtils.copy(ips,ops);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return map;
    }
}
