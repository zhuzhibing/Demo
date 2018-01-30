package net.luculent.automatioin.laks.services.article;

import net.luculent.automatioin.laks.platform.controller.BaseController;
import net.luculent.automatioin.laks.platform.domain.Result;
import net.luculent.automatioin.laks.services.article.service.IArticleTypeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/services/artp")
public class ArticleTypeController extends BaseController {

    @Resource
    private IArticleTypeService articleTypeService;


    @RequestMapping(value = "/queryByParentId")
    public Result queryArticleTypeByParentId(@RequestParam(value = "type", required = false) String type) throws Exception{

        List<Map<String, Object>> map = articleTypeService.queryByParentId(type);

        // 构造返回数据结构
        List<Map<String, Object>> result = new ArrayList<>();

        for (Map<String, Object> data : map) {

            result.add(typeConversion(data));

        }

        return this.success(result);

    }

    /**
     * 数据表字段格式化
     *
     * @param source
     * @return
     */
    private Map<String, Object> typeConversion(Map<String, Object> source) {

        Map<String, Object> target = new HashMap();

        if (source != null) {
            target.put("id", source.get("article_type")); // 类型编号
            target.put("name", source.get("artp_name")); // 名称
            target.put("pId", source.get("parent_id")); // 父级节点id
            target.put("description", source.get("artp_description")); // 描述
        }

        return target;
    }

}
