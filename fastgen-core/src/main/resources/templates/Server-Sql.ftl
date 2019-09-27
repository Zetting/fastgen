-- ----------------------------
-- 新增${tableComment}[${tableName}]-增删查改菜单和权限
    -- ----------------------------
    -- 新增菜单
    INSERT INTO `sys_menu` (`create_time`, `i_frame`, `name`, `component`, `pid`, `sort`, `icon`, `path`) VALUES (NOW(), 0, '${tableComment}管理', 'system/${groupName}/index', '1', '2', 'peoples', '${groupName}');

    -- 管理员分配权限
    INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES ((SELECT s._id from (SELECT id as _id from sys_menu WHERE name = '${tableComment}管理') as s), '1');

    -- 新增权限编码
    -- all
    INSERT INTO `sys_permission` (`alias`, `create_time`, `name`, `pid`) VALUES ('${tableComment}管理', NOW(), '${upperCaseClassName}_ALL', (SELECT s._id from (SELECT id as _id from sys_permission WHERE name = 'ADMIN') as s));
    -- 创建
    INSERT INTO `sys_permission` (`alias`, `create_time`, `name`, `pid`) VALUES ('${tableComment}创建', NOW(), '${upperCaseClassName}_CREATE', (SELECT s._id from (SELECT id as _id from sys_permission WHERE name = '${upperCaseClassName}_ALL') as s));
    -- 删除
    INSERT INTO `sys_permission` (`alias`, `create_time`, `name`, `pid`) VALUES ('${tableComment}删除', NOW(), '${upperCaseClassName}_DELETE', (SELECT s._id from (SELECT id as _id from sys_permission WHERE name = '${upperCaseClassName}_ALL') as s));
    INSERT INTO `sys_permission` (`alias`, `create_time`, `name`, `pid`) VALUES ('${tableComment}查询', NOW(), '${upperCaseClassName}_SELECT', (SELECT s._id from (SELECT id as _id from sys_permission WHERE name = '${upperCaseClassName}_ALL') as s));
    INSERT INTO `sys_permission` (`alias`, `create_time`, `name`, `pid`) VALUES ('${tableComment}修改', NOW(), '${upperCaseClassName}_EDIT', (SELECT s._id from (SELECT id as _id from sys_permission WHERE name = '${upperCaseClassName}_ALL') as s));

<#--start_config-->
#是否有效
enable=true
#文件生成路径
filePath=${resourcesPath}\\sql\\${groupName}\\${className}-dml.sql
<#--end_config-->
