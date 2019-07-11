package com.me.core.service;

import com.me.common.fdfs.FastDFSUtils;
import org.springframework.stereotype.Service;

@Service("uploadService")
public class UploadServiceImpl implements UploadService{
    //上传图片到FastDFS上 java 接口  FastDFSUtils工具类
    public String uploadPic(byte[] pic ,String name,long size) throws Exception {
        return FastDFSUtils.uploadPic(pic,name,size);

    }
}
