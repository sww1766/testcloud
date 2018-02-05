package com.rest;

import com.alibaba.fastjson.JSONObject;
import com.conf.ResponseBean;
import com.model.UserBean;
import com.service.Service;
import com.shiro.JWTUtil;
import com.util.TokenSingleton;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@RestController
@RequestMapping("authorize")
public class WebController {

    private static final Logger logger = LogManager.getLogger(WebController.class);

    private Service service;

    @Autowired
    public void setService(Service service) {
        this.service = service;
    }

    @GetMapping("/getToken")
    public ResponseBean login(@RequestParam("username") String username,
                              @RequestParam("password") String password) {
        UserBean userBean = service.getUser(username);
        if (userBean.getPassword().equals(password)) {
            if(TokenSingleton.getInstance().getMap()!=null && TokenSingleton.getInstance().getMap().get(username)!=null){
                JSONObject tokenObj = (JSONObject)TokenSingleton.getInstance().getMap().get(username);
                Long createTime = tokenObj.getLong("create_time");
                Long expireTime = tokenObj.getLong("expire_time");
                Long nowTime = System.currentTimeMillis();
                Lock lock = new ReentrantLock();
                lock.lock();
                // 当前时间减去创建时间减去5秒大于刷新时间，则刷新令牌,否则返回令牌
                if(nowTime-createTime-5000>expireTime){
                    lock.unlock();
                    logger.info(String.format("用户：%s 刷新令牌成功",username));
                    return new ResponseBean(HttpStatus.OK.value(), "authorize success", JWTUtil.createToken(username, password));
                }else {
                    logger.info(String.format("用户：%s 返回令牌成功",username));
                    return new ResponseBean(HttpStatus.OK.value(), "authorize success", tokenObj);
                }
            }else{
                logger.info(String.format("用户：%s 创建令牌成功",username));
                return new ResponseBean(HttpStatus.OK.value(), "authorize success", JWTUtil.createToken(username, password));
            }
        } else {
            return new ResponseBean(HttpStatus.UNAUTHORIZED.value(), "用户名或密码错误", null);
        }
    }

    @GetMapping("/article")
    public ResponseBean article() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            return new ResponseBean(200, "You are already logged in", null);
        } else {
            return new ResponseBean(200, "You are guest", null);
        }
    }

    @GetMapping("/require_auth")
    @RequiresAuthentication
    public ResponseBean requireAuth() {
        return new ResponseBean(200, "You are authenticated", null);
    }

    @GetMapping("/require_role")
    @RequiresRoles("admin")
    public ResponseBean requireRole() {
        return new ResponseBean(200, "You are visiting require_role", null);
    }

    @GetMapping("/require_permission")
    @RequiresPermissions(logical = Logical.AND, value = {"view", "edit"})
    public ResponseBean requirePermission() {
        return new ResponseBean(200, "You are visiting permission require edit,view", null);
    }

    @RequestMapping(path = "/401")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseBean unauthorized() {
        return new ResponseBean(401, "Unauthorized", null);
    }
}
