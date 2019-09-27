package com.fastgen.core.controller;

import com.fastgen.core.base.Response;
import com.fastgen.core.model.ColumnInfo;
import com.fastgen.core.model.TableInfo;
import com.fastgen.core.service.GenService;
import com.fastgen.core.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Zheng Jie
 * @date 2019-01-02
 */
@RestController
@RequestMapping("/generator")
public class GenController {

    @Autowired
    private GenService genService;

    /**
     * 查询数据库元数据
     *
     * @param keywords
     * @param page
     * @param size
     * @return
     */
    @GetMapping(value = "/tables")
    public Response getTables(@RequestParam(defaultValue = "") String keywords,
                              @RequestParam(defaultValue = "1") Integer page,
                              @RequestParam(defaultValue = "10") Integer size) {
        int[] startEnd = PageUtil.transToStartEnd(page, size);
        return Response.success(genService.getTables(keywords, startEnd), "成功");
    }

    /**
     * 查询表内元数据
     *
     * @param tableName
     * @return
     */
    @GetMapping(value = "/columns")
    public Response getTables(@RequestParam String tableName) {
        return Response.success(genService.getColumns(tableName), "成功");
    }

    /**
     * 生成代码
     *
     * @param columnInfos
     * @return
     */
    @PostMapping(value = "/gen")
    public Response gen(@RequestBody List<ColumnInfo> columnInfos,
                              @RequestParam String tableName) {
        TableInfo tableInfo = genService.getTableInfo(tableName);
        genService.gen(columnInfos, tableInfo);
        return Response.success();
    }

}
