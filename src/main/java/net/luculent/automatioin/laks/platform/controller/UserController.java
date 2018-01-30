//package net.luculent.automatioin.laks.platform.controller;
//
//import net.luculent.automatioin.laks.platform.domain.Result;
//import net.luculent.automatioin.laks.platform.enums.SysExpEnum;
//import net.luculent.automatioin.laks.platform.file.FileTransfer;
//import net.luculent.automatioin.laks.platform.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.annotation.Resource;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.UUID;
//
///**
// * Description 用户操作
// * Author chenmingming
// * CreateTime 2017-12-27 16:59
// **/
//@RestController
//@RequestMapping(value = "/platform")
//public class UserController implements TokenController {
//    @Autowired
//    private UserService userService;
//
//    @Resource(name = "ftpFileTransfer")
//    private FileTransfer ftpFileTransfer;
//
//    @Value("${laks.ftp.httpurl}")
//    private String httpurl;
//
//    /**
//     * 手机端修改个人信息
//     *
//     * @param userId
//     * @param userName
//     * @param org
//     * @param duty
//     * @param email
//     * @param mobile
//     * @param file
//     * @return
//     */
//    @PostMapping(value = "/updateUserForApp")
//    public Result<Object> updateUserForApp(@RequestParam("userId") Integer userId,
//                                           @RequestParam(value = "userName", required = false) String userName,
//                                           @RequestParam(value = "org", required = false) String org,
//                                           @RequestParam(value = "duty", required = false) String duty,
//                                           @RequestParam(value = "email", required = false) String email,
//                                           @RequestParam(value = "mobile", required = false) String mobile,
//                                           @RequestParam(value = "file", required = false) MultipartFile file) {
//        Result result = null;
//        Map<String, Object> params = new HashMap<String, Object>();
//        String newFileName = null;
//
//        try {
//            if (file != null && !file.isEmpty()) {
//                String contentType = file.getContentType();  // image/png(jpg,gif等)
//                String fileType[] = contentType.split("/");
//                newFileName = UUID.randomUUID().toString().replace("-", "");
//                if ("image".equals(fileType[0])) {
//                    newFileName = newFileName + "." + fileType[1];
//                    ftpFileTransfer.upload(file.getBytes(), newFileName);
//                    newFileName = httpurl + newFileName;
//                } else {
//                    return ResultUtil.error(SysExpEnum.UNKNOW_ERROR);
//                }
//            }
//
//            //保存用户信息
//            params.put("usr_id", userId);
//            params.put("usr_name", userName);
//            params.put("usr_org", org);
//            params.put("usr_duty", duty);
//            params.put("usr_email", email);
//            params.put("usr_mobile", mobile);
//            params.put("usr_photo", newFileName);
//            this.userService.updateUserById(params);
//
//            result = ResultUtil.success(this.userService.queryUserByIdForApp(userId));
//        } catch (Exception e) {
//            LogUtil.error(UserController.class, e.toString());
//            result = ResultUtil.error(SysExpEnum.UNKNOW_ERROR);
//        }
//        return result;
//    }
//}
