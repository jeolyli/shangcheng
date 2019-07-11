package com.me.core.controller;

import com.me.common.web.Constants;
import com.me.core.service.UploadService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * 上传单张
 * 多张
 * KindEditor的图片
 * */
@Controller
public class UploadController {

    @Autowired
    private UploadService uploadService;

    //上传品牌的单张图片
    @RequestMapping(value = "/upload/uploadPic.do")
    public void uploadPic(MultipartFile pic, HttpServletRequest request
    , HttpServletResponse response) throws  Exception{



        try {
            //保存到图片服务器
  /*          String realPath = request.getSession().getServletContext().getRealPath("")+"upload\\";
            File fileSourcePath = new File(realPath);
            String ext = FilenameUtils.getExtension(pic.getOriginalFilename());
            String path = UUID.randomUUID().toString()+"."+ext;
            if(!fileSourcePath.exists()){
                fileSourcePath.mkdirs();

            }
           // String url = realPath + path;
            //保存到磁盘
            File file = new File(fileSourcePath,path);
            pic.transferTo(file);*/

            String path = uploadService.uploadPic(pic.getBytes(),pic.getOriginalFilename(),pic.getSize());
            //全路径
            String url = Constants.img_url+path;



            JSONObject jo = new JSONObject();
          //  String url = "\\upload\\" +path;
            jo.put("path",url);
            //返回路径
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(jo.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    //上传商品多张图片
    @RequestMapping(value = "/upload/uploadPics.do")
    public @ResponseBody
    List<String> uploadPic(@RequestParam(required = false) MultipartFile[] pics) throws  Exception{
        //创建URL集群
        List<String> urls = new ArrayList<>();
        for (MultipartFile pic: pics){
            String path = uploadService.uploadPic(pic.getBytes(),pic.getOriginalFilename(),pic.getSize());
            //全路径
            String url = Constants.img_url + path;
            urls.add(url);
        }
        return urls;

    }

    //异步的上传图片（富文本编辑器的框架）
    @RequestMapping(value = "/upload/uploadFck.do")
    public void uploadFck(HttpServletRequest request,HttpServletResponse response) throws Exception {
        //无敌版接收文件或图片
        MultipartRequest mr = (MultipartRequest) request;
        //只有图片或文件 支持多张
        Map<String, MultipartFile> fileMap = mr.getFileMap();
        //遍历Map
        Set<Map.Entry<String, MultipartFile>> entries = fileMap.entrySet();
        for (Map.Entry<String,MultipartFile> entry: entries ) {
            MultipartFile pic = entry.getValue();
            //上传图片到FastDFS去
            String path = uploadService.uploadPic(pic.getBytes(),pic.getOriginalFilename(),pic.getSize());
            String url = Constants.img_url + path;
            JSONObject jo = new JSONObject();
            jo.put("url",url);
            jo.put("error",0);
            //返回路径
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(jo.toString());

        }



    }

}
