package com.fastgen.core.service;

import com.fastgen.core.model.ColumnInfo;
import com.fastgen.core.model.TableInfo;

import java.util.List;

/**
 * 生成服务类
 *
 * @author zet
 * @date 2019-01-02
 */
public interface GenService {
    /**
     * 获取表信息
     *
     * @param tableName
     * @return
     */
    TableInfo getTableInfo(String tableName);

    /**
     * 查询数据库元数据
     *
     * @param name
     * @param startEnd
     * @return
     */
    Object getTables(String name, int[] startEnd);

    /**
     * 得到数据表的元数据
     *
     * @param name
     * @return
     */
    Object getColumns(String name);

    /**
     * 生成代码
     *
     * @param columnInfos
     * @param tableInfo
     */
    void gen(List<ColumnInfo> columnInfos, TableInfo tableInfo);

}
