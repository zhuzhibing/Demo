package net.luculent.automatioin.laks.platform.controller;

import net.luculent.automatioin.laks.platform.domain.Result;
import net.luculent.automatioin.laks.platform.file.FileTransfer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.UUID;

/**
 * @Author: zhuzb
 * @Description:
 * @Date Create In 19:19 2018/1/30
 * @Modified By:
 */
@RestController
@RequestMapping(value = "/platform/file")
public class FileController extends BaseController implements TokenController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Resource(name = "ftpFileTransfer")
    private FileTransfer ftpFileTransfer;

    @PostMapping(value = "/upload")
    public Result upload(@RequestParam MultipartFile file) {

        // 获取文件类型  image/png
        String contentType = file.getContentType();
        String fileType[] = contentType.split("/");

        // 文件存储的文件名
        String newFileName = UUID.randomUUID().toString().replace("-", "");

        newFileName = newFileName + "." + fileType[1];
        try {
            boolean flag = ftpFileTransfer.upload(file.getBytes(), newFileName);
            if (!flag) {
                return this.message(20202, "文件上传ftp失败");
            }
        } catch (IOException e) {
            logger.error("ftpFileTransfer == >> ", e);
            return this.message(20203, "");
        }

        return this.success();

    }
}
