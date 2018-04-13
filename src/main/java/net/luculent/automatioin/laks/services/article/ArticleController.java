package net.luculent.automatioin.laks.services.article;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import net.luculent.automatioin.laks.platform.controller.BaseController;
import net.luculent.automatioin.laks.platform.controller.TokenController;
import net.luculent.automatioin.laks.platform.domain.Result;
import net.luculent.automatioin.laks.platform.file.FileTransfer;
import net.luculent.automatioin.laks.services.article.service.IArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/services")
public class ArticleController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);

    /**
     * 图片http访问的路径
     */
    @Value("${laks.ftp.httpurl}")
    private String httpurl;

    /**
     * 图片存储的文件夹
     */
    private static final String FOLDER = "/img/";

    @Resource
    private IArticleService articleService;


    @Resource(name = "ftpFileTransfer")
    private FileTransfer ftpFileTransfer;

    /**
     * 文章保存
     *
     * @param atcSeq
     * @param title
     * @param summary
     * @param content
     * @param source
     * @param file
     * @param type
     * @param isComment
     * @return
     */
    @PostMapping(value = "/article/save")
    public Result save(@RequestParam(value = "atcSeq", required = false) int atcSeq,
                       @RequestParam String title,
                       @RequestParam String summary,
                       @RequestParam String content,
                       @RequestParam String source,
                       @RequestParam MultipartFile file,
                       @RequestParam String type,
                       @RequestParam String isComment) throws Exception {

        // 获取文件类型  image/png
        String contentType = file.getContentType();
        String fileType[] = contentType.split("/");

        // 文件存储的文件名
        String newFileName = UUID.randomUUID().toString().replace("-", "");

        if ("image".equals(fileType[0])) {

            newFileName = newFileName + "." + fileType[1];
            try {
                boolean flag = ftpFileTransfer.upload(file.getBytes(), FOLDER + newFileName);
                if (!flag) {
                    return this.message(20202, "文件上传ftp失败");
                }
            } catch (IOException e) {
                logger.error("ftpFileTransfer == >> ", e);
                return this.message(20203, e.getMessage());
            }

        } else {
            return new Result(20201, "文件类型不匹配");
        }

        Map<String, Object> article = new HashMap<String, Object>();

        article.put("atcSeq", atcSeq);
        article.put("atcTitle", title);
        article.put("atcSummary", summary);
        article.put("atcContent", content);
        article.put("atcSource", source);
        article.put("imgUrl", httpurl + FOLDER + newFileName);
        article.put("actType", type);
        article.put("isComment", isComment); // 是否可评论

        int b = articleService.save(article);

        if (logger.isInfoEnabled()) {
            logger.info("atc_seq === >> " + article.get("atcSeq"));
        }

        return this.success(b);

    }

    /**
     * 文章删除
     *
     * @param atcSeq
     * @return
     */
    @RequestMapping(value = "/article/delete")
    public Result delete(@RequestParam(value = "atcSeq") int atcSeq) throws Exception {

        int b = articleService.delete(atcSeq);

        return this.success(b);

    }

    /**
     * 根据文章编号查询文章信息
     *
     * @param atcSeq
     * @return
     */
    @RequestMapping(value = "/article/query")
    public Result queryByAtcSeq(@RequestParam(value = "atcSeq") int atcSeq) throws Exception {

        Map<String, Object> article = articleService.queryOne(atcSeq);

        // 构造返回数据结构
        Map<String, Object> result = typeConversion(article);

        return this.success(result);

    }

    /**
     * 文章分页查询
     *
     * @param body
     * @return
     */
    @PostMapping(value = "/article/getPageInfo")
    public Result getPageInfo(@RequestBody JSONObject body) throws Exception {

        if (logger.isInfoEnabled()) {
            logger.info("RequestBody === >> " + body);
        }

//        JSONObject obj = JSON.parseObject(body);

        String type = body.getString("type");

        String title = body.getString("title");

        JSONObject pageInfo = body.getJSONObject("pageInfo");

        int pageNo = pageInfo.getInteger("pageNo");

        int pageSize = pageInfo.getInteger("pageSize");

        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("atcTitle", title);
        condition.put("atcType", type);

        PageInfo result = articleService.queryArticlePageInfo(condition, pageNo, pageSize);

        return this.success(result);
    }

    /**
     * 根据类型查询文章列表
     *
     * @param atcType
     * @return
     */
    @RequestMapping(value = "/article/queryByType")
    public Result queryByAtcType(@RequestParam(value = "atcType") String atcType) throws Exception {

        List<Map<String, Object>> queryData = articleService.queryList(atcType);

        // 构造返回数据结构
        List<Map<String, Object>> result = new ArrayList<>();

        for (Map<String, Object> article : queryData) {

            result.add(typeConversion(article));

        }

        return this.success(result);

    }

    /**
     * 数据表数据字段格式化
     *
     * @param article
     * @return
     */
    private Map<String, Object> typeConversion(Map<String, Object> article) {

        Map<String, Object> map = new HashMap<>();

        if (article != null) {
            map.put("atcSeq", article.get("atc_seq")); // 编号
            map.put("title", article.get("atc_title")); //标题
            map.put("summary", article.get("atc_summary")); // 概述
            map.put("content", article.get("atc_content")); //内容
            map.put("source", article.get("atc_source")); // 来源
            map.put("imgUrl", article.get("img_url")); // 图片地址
            map.put("type", article.get("act_type"));  // 类型
            map.put("isComment", article.get("is_comment")); // 是否可评论
            map.put("createBy", article.get("usr_name"));  // 创建人
            map.put("createTime", article.get("fstusr_dtm"));  // 创建时间
        }

        return map;
    }


}
