package com.imooc.controller.center;

import com.imooc.pojo.Users;
import com.imooc.pojo.bo.center.CenterUserBO;
import com.imooc.resource.FileUpload;
import com.imooc.service.CenterUserService;
import com.imooc.utils.CommonResult;
import com.imooc.utils.CookieUtils;
import com.imooc.utils.DateUtil;
import com.imooc.utils.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gengbin
 * @date 2021/2/12
 */
@Api(tags = "用户中心相关接口")
@RestController
@RequestMapping("userinfo")
public class CenterUserController {
    @Autowired
    private FileUpload fileUpload;
    @Autowired
    private CenterUserService centerUserService;

    @ApiOperation(value = "用户头像修改", notes = "用户头像修改", httpMethod = "POST")
    @PostMapping("/uploadFace")
    public CommonResult uploadFace(@RequestParam String userId, MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        String fileSpace = fileUpload.getImageUserFaceLocation();
        String uploadPathPrefix = File.separator + userId;
        if (file != null) {
            FileOutputStream fileOutputStream = null;
            try {
                String filename = file.getOriginalFilename();
                if (StringUtils.isNotBlank(filename)) {
                    String[] fileNameArr = filename.split("\\.");
                    String suffix = fileNameArr[fileNameArr.length - 1];

                    if (!suffix.equalsIgnoreCase("png") && !suffix.equalsIgnoreCase("png") && !suffix.equalsIgnoreCase("png")) {
                        return CommonResult.ok("图片格式不正确！");
                    }

                    String newFileName = "face-" + userId + "." + suffix;
                    String finalFacePath = fileSpace + uploadPathPrefix + File.separator + newFileName;
                    uploadPathPrefix += ("/" + newFileName);
                    File outFile = new File(finalFacePath);
                    if (outFile.getParentFile() != null) {
                        outFile.getParentFile().mkdirs();
                    }
                    fileOutputStream = new FileOutputStream(outFile);
                    InputStream inputStream = file.getInputStream();
                    IOUtils.copy(inputStream, fileOutputStream);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.flush();
                        fileOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            return CommonResult.errorMsg("文件不能为空！");
        }
        String imagServerUrl = fileUpload.getImagServerUrl();
        String finalUserFaceUrl = imagServerUrl + uploadPathPrefix + "?t=" + DateUtil.getCurrentDateString(DateUtil.DATE_PATTERN);
        Users user = centerUserService.uploadUserFace(userId, finalUserFaceUrl);
        user = this.setNullProperty(user);
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(user), true);
        //TODO 后续增加令牌token，整合进redis，分布式会话
        return CommonResult.ok();
    }

    @ApiOperation(value = "更新用户信息", notes = "更新用户信息", httpMethod = "POST")
    @PostMapping("updateUserInfo")
    public CommonResult updateUserInfo(String userId, @RequestBody CenterUserBO centerUserBO, BindingResult result, HttpServletRequest request, HttpServletResponse response) {
        if (result.hasErrors()) {
            Map<String, String> errorMap = getErrors(result);
            return CommonResult.errorMap(errorMap);
        }
        Users user = centerUserService.updateUserInfo(userId, centerUserBO);
        user = this.setNullProperty(user);
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(user), true);
        //TODO 后续增加令牌token，整合进redis，分布式会话
        return CommonResult.ok();
    }

    private Map<String, String> getErrors(BindingResult result) {
        HashMap<String, String> map = new HashMap<>();
        List<FieldError> fieldErrorList = result.getFieldErrors();
        for (FieldError error : fieldErrorList) {
            String errorField = error.getField();
            String errorMessage = error.getDefaultMessage();
            map.put(errorField, errorMessage);
        }
        return map;
    }

    private Users setNullProperty(Users user) {
        user.setPassword(null);
        user.setMobile(null);
        user.setEmail(null);
        user.setCreatedTime(null);
        user.setUpdatedTime(null);
        user.setBirthday(null);
        return user;
    }
}
