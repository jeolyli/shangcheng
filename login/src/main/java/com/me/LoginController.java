package com.me;

import com.me.common.RequestUtils;
import com.me.core.pojo.user.Buyer;
import com.me.core.service.BuyerService;
import com.me.core.service.SessionProvider;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 登录
 * */
@Controller
public class LoginController {
    @Autowired
    private SessionProvider sessionProvider;
    @Autowired
    private BuyerService buyerService;
    //去登录
    @RequestMapping(value = "/login.aspx",method = RequestMethod.GET)
    public String login(){
        return "login";
    }
    //提交登录页面
    @RequestMapping(value = "/login.aspx",method = RequestMethod.POST)
    public String login(String username, String password, String ReturnUrl, Model model
    , HttpServletRequest request, HttpServletResponse response){
        if(null != username ){
            if(null != password){
                Buyer buyer = buyerService.selectBuyerByUsername(username);
                if(null != buyer){
                    if(buyer.getPassword().equals(encodePassword(password))){
                        //保存用户到session中 用户名放到session中
                        sessionProvider.setAttributeForUserName(RequestUtils.getCESSION(request,response),username);

                    }else{
                        model.addAttribute("error","密码错误");
                    }
                }else{
                    model.addAttribute("error","用户名不存在");
                }
            }else{
                model.addAttribute("error","密码不能为空");
            }
        }else {
            model.addAttribute("error","用户名不能为空");
        }
        return "login";
    }
    //加密MD5+16进制+盐
    public String encodePassword(String password){
        String algorithm = "MD5";
        byte[] encodeHex = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            //md5加密后密文
           byte[] digest =  messageDigest.digest(password.getBytes());
           //十六进制
            encodeHex = Hex.encode(digest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String(encodeHex);
    }
    //是否已经登录
    @RequestMapping(value = "/isLogin.aspx")
    public @ResponseBody
    MappingJacksonValue isLogin(String callback,HttpServletRequest request,
                                HttpServletResponse response){
        Integer result = 0;
        String username = sessionProvider.getAttributeForUserName(RequestUtils.getCESSION(request,response));
        if(null != username){
            result = 1;
        }
        MappingJacksonValue mjv = new MappingJacksonValue(result);
        mjv.setJsonpFunction(callback);
        return mjv;
    }
}
