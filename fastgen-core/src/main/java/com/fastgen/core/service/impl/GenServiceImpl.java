package com.fastgen.core.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.fastgen.core.base.Contants;
import com.fastgen.core.base.ServerException;
import com.fastgen.core.base.SysVariableEnum;
import com.fastgen.core.base.cfgs.CustomMapsCfgs;
import com.fastgen.core.contract.DynamicFormConfigVO;
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
    private CustomMapsCfgs customMapsCfgs;

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
        Map<String, Object> genConfig = configUtil.getConfigBean(Contants.USER_CFG);

        String templates = genConfig.get(Contants.FIELD_NAME_TEMPLATES) + "";
        if (templates == null || "".equals(templates)) {
            throw new ServerException("模板未选择");
        }
        List<String> templateList = Arrays.asList(templates.split(Chars.COMMA));
        Map<String, Object> variableMaps = getSysVariableValue(columnInfos, genConfig, tableInfo);
        for (String templateName : templateList) {
            TemplateFtlInfo templateFtlInfo = configService.getTemplateInfo(variableMaps, templateName);
            if (Objects.isNull(templateFtlInfo)) {
                continue;
            }

            File file = new File(templateFtlInfo.getFilePath());
            // 如果非覆盖生成
            String conver = (String) genConfig.get(Contants.FIELD_NAME_COVER);
            if (!conver.equals("true")) {
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
                finalFileContent = freemarkerUtil.parse(templateFtlInfo.getTemplateContent(), variableMaps);
            } catch (Exception e) {
                log.error("转换异常", e);
                throw new ServerException("转换异常");
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
     * 获取系统变量
     *
     * @param columnInfos
     * @param genConfig
     * @param tableInfo
     * @return
     */
    private Map<String, Object> getSysVariableValue(List<ColumnInfo> columnInfos, Map<String, Object> genConfig, TableInfo tableInfo) {
        String tableName = tableInfo.getTableName();
        String tableComment = tableInfo.getTableComment();

        String groupName = tableName.split("_")[0];//todo

        //常规变量
        Map<String, Object> variableMaps = new HashMap();
        variableMaps.put(SysVariableEnum.GROUP_NAME.getName(), groupName);
        variableMaps.put(SysVariableEnum.AUTHOR.getName(), genConfig.get(Contants.FIELD_NAME_AUTHOR));
        variableMaps.put(SysVariableEnum.DATE.getName(), LocalDate.now().toString());
        variableMaps.put(SysVariableEnum.TABLE_NAME.getName(), tableName);
        variableMaps.put(SysVariableEnum.TABLE_COMMENT.getName(), StringUtils.removeEnd(tableComment, "表"));
        String className = StringUtils.toCapitalizeCamelCase(tableName);
        String changeClassName = StringUtils.toCamelCase(tableName);
        variableMaps.put(SysVariableEnum.CLASS_NAME.getName(), className);
        variableMaps.put(SysVariableEnum.FIRST_LOWER_CLASS_NAME.getName(), StrUtil.lowerFirst(className));
        variableMaps.put(SysVariableEnum.UPPER_CASE_CLASS_NAME.getName(), className.toUpperCase());
        variableMaps.put(SysVariableEnum.CAMEL_CASE_CLASS_NAME.getName(), changeClassName);
        variableMaps.put(SysVariableEnum.HAS_TIMESTAMP.getName(), false);
        variableMaps.put(SysVariableEnum.HAS_BIGDECIMAL.getName(), false);
        variableMaps.put(SysVariableEnum.HAS_QUERY.getName(), false);
        variableMaps.put(SysVariableEnum.HAS_AUTO.getName(), false);

        //列变量
        List<Map<String, Object>> columns = new ArrayList<>();
        List<Map<String, Object>> queryColumns = new ArrayList<>();
        for (ColumnInfo column : columnInfos) {
            Map<String, Object> listMap = new HashMap();
            listMap.put(SysVariableEnum.COLUMN_COLUMN_COMMENT.getName(), column.getColumnComment());
            listMap.put(SysVariableEnum.COLUMN_COLUMN_KEY.getName(), column.getColumnKey());

            String colType = configUtil.cloToJava(column.getColumnType().toString());
            String camelCaseColumnName = StringUtils.toCamelCase(column.getColumnName().toString());
            String capitalColumnName = StringUtils.toCapitalizeCamelCase(column.getColumnName().toString());
            String underScoreCaseColumnName = StringUtils.toUnderScoreCase(column.getColumnName().toString());
            if (PK.equals(column.getColumnKey())) {
                variableMaps.put(SysVariableEnum.COLUMN_PKCOLUMNTYPE.getName(), colType);
                variableMaps.put(SysVariableEnum.COLUMN_PKCOLCAMELCASENAME.getName(), camelCaseColumnName);
                variableMaps.put(SysVariableEnum.COLUMN_PKCAPITALCOLNAME.getName(), capitalColumnName);
            }
            if (TIMESTAMP.equals(colType)) {
                variableMaps.put(SysVariableEnum.HAS_TIMESTAMP.getName(), true);
            }
            if (BIGDECIMAL.equals(colType)) {
                variableMaps.put(SysVariableEnum.HAS_BIGDECIMAL.getName(), true);
            }
            if (EXTRA.equals(column.getExtra())) {
                variableMaps.put(SysVariableEnum.HAS_AUTO.getName(), true);
            }
            listMap.put(SysVariableEnum.COLUMN_COLUMNTYPE.getName(), colType);
            listMap.put(SysVariableEnum.COLUMN_COLUMNNAME.getName(), column.getColumnName());
            listMap.put(SysVariableEnum.COLUMN_ISNULLABLE.getName(), column.getIsNullable());
            listMap.put(SysVariableEnum.COLUMN_COLUMNSHOW.getName(), column.getColumnShow());
            listMap.put(SysVariableEnum.COLUMN_PKCOLCAMELCASENAME.getName(), camelCaseColumnName);
            variableMaps.put(SysVariableEnum.COLUMN_PKCAPITALCOLNAME.getName(), capitalColumnName);
            listMap.put(SysVariableEnum.COLUMN_UNDERSCORECASECOLUMNNAME.getName(), underScoreCaseColumnName);

            if (!StringUtils.isBlank(column.getColumnQuery())) {
                listMap.put(SysVariableEnum.COLUMN_COLUMNQUERY.getName(), column.getColumnQuery());
                variableMaps.put(SysVariableEnum.HAS_QUERY.getName(), true);
                queryColumns.add(listMap);
            }
            columns.add(listMap);
        }
        variableMaps.put(SysVariableEnum.COLUMNS.getName(), columns);
        variableMaps.put(SysVariableEnum.COLUMN_QUERYCOLUMNS.getName(), queryColumns);

        //动态组件变量
        List<DynamicFormConfigVO> dynamicConfigs = JSONUtil.toList(
                JSONUtil.parseArray(genConfig.get(Contants.FIELD_NAME_DYNAMICFORM)), DynamicFormConfigVO.class);
        putDynamicConfigs(variableMaps, dynamicConfigs);


        //        // 判断是否去除表前缀
//        if (StringUtils.isNotEmpty(genConfig.getPrefix())) {
//            className = StringUtils.toCapitalizeCamelCase(StrUtil.removePrefix(tableName, genConfig.getPrefix()));
//            changeClassName = StringUtils.toCamelCase(StrUtil.removePrefix(tableName, genConfig.getPrefix()));
//        }//todo

        //设置自定变量
        putCustomConfigs(variableMaps);
        return variableMaps;
    }

    /**
     * 设置动态组件变量
     *
     * @param variableMaps
     * @param dynamicConfigs
     */
    private void putDynamicConfigs(Map<String, Object> variableMaps, List<DynamicFormConfigVO> dynamicConfigs) {
        if (Objects.isNull(dynamicConfigs) || dynamicConfigs.size() == 0) {
            return;
        }
        for (DynamicFormConfigVO dynamicConfig : dynamicConfigs) {
            Object value = paraseValue(variableMaps, dynamicConfig.getComponentValue());
            variableMaps.put(dynamicConfig.getComponentName(), value);
        }
    }

    /**
     * 自定义变量，可在模板直接用
     *
     * @param variableMaps
     */
    private void putCustomConfigs(Map<String, Object> variableMaps) {
        if (Objects.isNull(customMapsCfgs.getMaps()) || customMapsCfgs.getMaps().size() == 0) {
            return;
        }
        Map<String, Object> customMaps = customMapsCfgs.getMaps();
        for (String key : customMaps.keySet()) {
            Object valueObj = customMaps.get(key);
            Object value = "";
            if (Objects.nonNull(valueObj)) {
                value = paraseValue(variableMaps, valueObj.toString());
            }
            variableMaps.put(key, value);
        }
    }

    /**
     * 转换配置项的值
     *
     * @param variableMaps
     * @param originValue
     * @return
     */
    private Object paraseValue(Map<String, Object> variableMaps, String originValue) {
        if (StrUtil.isBlank(originValue)) {
            return originValue;
        }
        boolean shouldParase = StrUtil.containsAny(originValue, "${");
        String value = null;
        if (shouldParase) {
            try {
                value = freemarkerUtil.parse(originValue, variableMaps);
            } catch (Exception e) {
                log.error("转换异常");
            }
        }
        boolean containedPath = StrUtil.contains(originValue, '\\');
        if (containedPath) {
            value = originValue.replace("\\", "\\\\");
        }
        return value;
    }
}
