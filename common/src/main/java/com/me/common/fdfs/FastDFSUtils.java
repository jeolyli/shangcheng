package com.me.common.fdfs;

import org.apache.commons.io.FilenameUtils;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.springframework.core.io.ClassPathResource;

/**
 * 上传图片
 * */
public class FastDFSUtils {
    //上传
    public static String uploadPic(byte[] pic ,String name,long size) throws Exception {
        //spring公司提供类
        ClassPathResource resource = new ClassPathResource("fdfs_client.conf");

        //加载全局配置文件
        ClientGlobal.init(resource.getClassLoader().getResource("fdfs_client.conf").getPath());
        //连接tracker
        TrackerClient trackerClient = new TrackerClient();
        //获取Storage的地址
        TrackerServer trackerServer = trackerClient.getConnection();
        //连接Storage的服务器
        //拓展
        StorageServer storageServer = null;
        StorageClient1 storageClient1 = new StorageClient1(trackerServer,storageServer);

        String ext = FilenameUtils.getExtension(name);
        NameValuePair[] meta_list = new NameValuePair[3];
        meta_list[0] = new NameValuePair("filename",name);
        meta_list[1] = new NameValuePair("fileext",ext);
        meta_list[2] = new NameValuePair("filesize",String.valueOf(size));
        //  group1/M00/00/01/wKjIgFWOYc6APpjAAAD-qk29i78248.jpg

        //上传图片
        String path = storageClient1.upload_file1(pic,ext,meta_list);
        return path;


    }
}
