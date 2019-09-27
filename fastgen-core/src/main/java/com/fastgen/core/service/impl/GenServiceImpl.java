package com.fastgen.core.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.fastgen.core.base.Contants;
import com.fastgen.core.base.ServerException;
import com.fastgen.core.base.cfgs.SettingsMapsCfgs;
import com.fastgen.core.contract.vo.GenConfig;
import com.fastgen.core.model.ColumnInfo;
import com.fastgen.core.model.TableInfo;
import com.fastgen.core.model.TemplateFtlInfo;
import com.fastgen.core.service.ConfigService;
import com.fastgen.core.service.GenService;
import com.fastgen.core.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.File;
import java.time.LocalDate;
import java.util.*;

/**
 * 代码生成逻辑
 *
 * @author zet
 * @date 2019-01-02
 */
@Slf4j
@Service
public class GenServiceImpl implements GenService {
    private static final String TIMESTAMP = "Timestamp";
    private static final String BIGDECIMAL = "BigDecimal";
    private static final String PK = "PRI";
    private static final String EXTRA = "auto_increment";

    @PersistenceContext
    private EntityManager em;
    @Value("${spring.profiles.active}")
    private String active;
    @Autowired
    private ConfigUtil configUtil;
    @Autowired
    private ConfigService configService;
    @Autowired
    private FreemarkerUtil freemarkerUtil;
    @Autowired
    private SettingsMapsCfgs settingsMapsCfgs;

    @Override
    public TableInfo getTableInfo(String tableName) {
        StringBuilder sql = new StringBuilder("select table_name tableName,table_comment tableComment,create_time createTime from information_schema.tables where table_schema = (select database()) ");
        sql.append(String.format("AND table_name = '%s'", tableName));
        Query query = em.createNativeQuery(sql.toString());
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result) && result.size() == 1) {
            Object[] obj = result.get(0);
            return new TableInfo((String) obj[0], (String) obj[1], obj[2]);
        }

        return null;
    }

    @Override
    public Object getTables(String keywords, int[] startEnd) {
        StringBuilder sql = new StringBuilder("select table_name tableName,table_comment tableComment,create_time createTime from information_schema.tables where table_schema = (select database()) ");
        if (!ObjectUtils.isEmpty(keywords)) {
            sql.append("and (table_name like '%" + keywords + "%' ");
            sql.append("or table_comment like '%" + keywords + "%') ");
        }
        sql.append("order by table_name");
        Query query = em.createNativeQuery(sql.toString());
        query.setFirstResult(startEnd[0]);
        query.setMaxResults(startEnd[1] - startEnd[0]);

        System.out.println(sql.toString());
        List<Object[]> result = query.getResultList();
        List<TableInfo> tableInfos = new ArrayList<>();
        for (Object[] obj : result) {
            tableInfos.add(new TableInfo((String) obj[0], (String) obj[1], obj[2]));
        }
        Query query1 = em.createNativeQuery("SELECT COUNT(*) from information_schema.tables where table_schema = (select database())");
        Object totalElements = query1.getSingleResult();
        return toPage(tableInfos, totalElements);
    }


    @Override
    public Object getColumns(String name) {
        StringBuilder sql = new StringBuilder("select column_name, is_nullable, data_type, column_comment, column_key, extra from information_schema.columns where ");
        if (!ObjectUtils.isEmpty(name)) {
            sql.append("table_name = '" + name + "' ");
        }
        sql.append("and table_schema = (select database()) order by ordinal_position");
        Query query = em.createNativeQuery(sql.toString());
        List<Object[]> result = query.getResultList();
        List<ColumnInfo> columnInfos = new ArrayList<>();
        for (Object[] obj : result) {
            columnInfos.add(new ColumnInfo(obj[0], obj[1], obj[2], obj[3], obj[4], obj[5], null, "true"));
        }
        return toPage(columnInfos, columnInfos.size());
    }

    @Override
    public void gen(List<ColumnInfo> columnInfos, TableInfo tableInfo) {
        GenConfig genConfig = configUtil.getConfigBean(Contants.USER_CFG);

        String templates = genConfig.getTemplates();
        if (templates == null || "".equals(templates)) {
            throw new ServerException("模板未选择");
        }
        List<String> templateList = Arrays.asList(templates.split(Chars.COMMA));
        Map<String, Object> templateValues = getTemplateValue(columnInfos, genConfig, tableInfo);
        List<TemplateFtlInfo> templateFtlInfos = configService.templateInfos(templateValues);

        for (String templateName : templateList) {
            TemplateFtlInfo templateFtlInfo = configService.getTemplateFtlInfo(templateFtlInfos, templateName);
            if (Objects.isNull(templateFtlInfo)) {
                continue;
            }

            File file = new File(templateFtlInfo.getFilePath());
            // 如果非覆盖生成
            if (!genConfig.getCover()) {
                if (FileUtil.exist(file)) {
                    log.info("[file is exist] path = {}", templateFtlInfo.getFilePath());
                    continue;
                }
            }
            if (file.getParentFile().isDirectory()) {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
            }

            String finalFileContent = "";
            try {
                finalFileContent = freemarkerUtil.parse(templateFtlInfo.getTemplateContent(), templateValues);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // 生成代码
            FileUtil.writeString(finalFileContent, templateFtlInfo.getFilePath(), Contants.CHARSET_CODE);
            log.info("[gen success] path = {}", templateFtlInfo.getFilePath());
        }
    }

    /**
     * @param object
     * @param totalElements
     * @return
     */
    public static Map toPage(Object object, Object totalElements) {
        Map map = new HashMap();
        map.put("content", object);
        map.put("totalElements", totalElements);
        return map;
    }

    /**
     * 设置模板变量
     * @param columnInfos
     * @param genConfig
     * @param tableInfo
     * @return
     */
    private Map<String, Object> getTemplateValue(List<ColumnInfo> columnInfos, GenConfig genConfig, TableInfo tableInfo) {
        String tableName = tableInfo.getTableName();
        String tableComment = tableInfo.getTableComment();

        String groupName = StringUtils.isNotEmpty(genConfig.getGroupName())
                ? genConfig.getGroupName() : tableName.split("_")[0];


        Map<String, Object> map = new HashMap();
        map.put("package", genConfig.getPack());
        map.put("packagePath", genConfig.getPack().replace(".", "//"));
        map.put("groupName", groupName);
        map.put("author", genConfig.getAuthor());
        map.put("date", LocalDate.now().toString());
        map.put("tableName", tableName);
        map.put("tableComment", StringUtils.removeEnd(tableComment, "表"));
        map.put("genMode", genConfig.getGenMode());
        String className = StringUtils.toCapitalizeCamelCase(tableName);
        String changeClassName = StringUtils.toCamelCase(tableName);

        // 判断是否去除表前缀
        if (StringUtils.isNotEmpty(genConfig.getPrefix())) {
            className = StringUtils.toCapitalizeCamelCase(StrUtil.removePrefix(tableName, genConfig.getPrefix()));
            changeClassName = StringUtils.toCamelCase(StrUtil.removePrefix(tableName, genConfig.getPrefix()));
        }
        map.put("className", className);
        map.put("firstLowerClassName",  StrUtil.lowerFirst(className));
        map.put("upperCaseClassName", className.toUpperCase());
        map.put("changeClassName", changeClassName);
        map.put("hasTimestamp", false);
        map.put("hasBigDecimal", false);
        map.put("hasQuery", false);
        map.put("auto", false);

        List<Map<String, Object>> columns = new ArrayList<>();
        List<Map<String, Object>> queryColumns = new ArrayList<>();
        for (ColumnInfo column : columnInfos) {
            Map<String, Object> listMap = new HashMap();
            listMap.put("columnComment", column.getColumnComment());
            listMap.put("columnKey", column.getColumnKey());

            String colType = configUtil.cloToJava(column.getColumnType().toString());
            String changeColumnName = StringUtils.toCamelCase(column.getColumnName().toString());
            String capitalColumnName = StringUtils.toCapitalizeCamelCase(column.getColumnName().toString());
            String underScoreCaseColumnName = StringUtils.toUnderScoreCase(column.getColumnName().toString());
            if (PK.equals(column.getColumnKey())) {
                map.put("pkColumnType", colType);
                map.put("pkChangeColName", changeColumnName);
                map.put("pkCapitalColName", capitalColumnName);
            }
            if (TIMESTAMP.equals(colType)) {
                map.put("hasTimestamp", true);
            }
            if (BIGDECIMAL.equals(colType)) {
                map.put("hasBigDecimal", true);
            }
            if (EXTRA.equals(column.getExtra())) {
                map.put("auto", true);
            }
            listMap.put("columnType", colType);
            listMap.put("columnName", column.getColumnName());
            listMap.put("isNullable", column.getIsNullable());
            listMap.put("columnShow", column.getColumnShow());
            listMap.put("changeColumnName", changeColumnName);
            listMap.put("capitalColumnName", capitalColumnName);
            listMap.put("underScoreCaseColumnName", underScoreCaseColumnName);

            if (!StringUtils.isBlank(column.getColumnQuery())) {
                listMap.put("columnQuery", column.getColumnQuery());
                map.put("hasQuery", true);
                queryColumns.add(listMap);
            }
            columns.add(listMap);
        }
        map.put("columns", columns);
        map.put("queryColumns", queryColumns);

        //config
        map.putAll(JSONUtil.toBean(JSONUtil.toJsonStr(genConfig), Map.class));

        String serverPath = genConfig.getServerPath().replace("\\", "\\\\");
        String frontPath = genConfig.getFrontPath().replace("\\", "\\\\");
        map.put("javaPath", serverPath + Contants.PREFIX_SRC_MAIN_JAVA);
        map.put("resourcesPath", serverPath + Contants.PREFIX_SRC_MAIN_RESOURCES);
        map.put("frontPath", frontPath);
        map.put("serverPath", serverPath);


        //设置自定变量
        putSettings(map);
        return map;
    }

    /**
     * 自定义变量，可在模板直接用
     *
     * @param map
     */
    private void putSettings(Map<String, Object> map) {
        if (Objects.nonNull(settingsMapsCfgs.getMaps())) {
            map.putAll(settingsMapsCfgs.getMaps());
        }

    }
}
