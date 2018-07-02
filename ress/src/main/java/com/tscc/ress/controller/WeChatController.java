package com.tscc.ress.controller;

import com.tscc.ress.enums.ResultEnum;
import com.tscc.ress.exception.SellException;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLEncoder;

/**
 * 描述:微信授权的controller
 *
 * @author C
 * Date: 2018-07-01
 * Time: 13:17
 */
@Slf4j
@Controller
@RequestMapping("/wechat")
public class WeChatController {

    @Autowired
    private WxMpService wxMpService;

    /**
     * 授权方法第一步
     * @param returnUrl 携带的一个url这里用不到 只用来传递
     * @return 授权方法第一步转换后的路径
     */
    @GetMapping ("/authorize")
    public String authorize(@RequestParam("returnUrl")String returnUrl){

        //调用方法
        String url = "http://tscc.natapp1.cc/sell/wechat/userInfo";
        String redirectUrl = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_BASE, URLEncoder.encode(returnUrl));
        return  "redirect:" + redirectUrl;
    }

    /**
     * 授权方法第二步
     * @param code 得到Token的必要参数  通过第一步得到
     * @param returnUrl  第一步中传过来的最终重定向的路径
     * @return 返回最终重定向的路径+openid
     */
    @GetMapping("/userInfo")
    public String userInfo(@RequestParam("code") String code,
                         @RequestParam("state") String returnUrl ){
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken ;
        try{
             wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        }catch (WxErrorException e){
            log.error("【微信网页授权】 {}", e);
            throw new SellException(ResultEnum.WECHAT_MP_ERROR.getCode(),e.getError().getErrorMsg());
        }

        String openId = wxMpOAuth2AccessToken.getOpenId();
        return "redirect:" + returnUrl + "?openid=" + openId;
    }

}
