<template>
  <div>
    <el-button size="mini" type="primary" icon="el-icon-edit" @click="to"/>
    <eForm ref="form" :sup_this="sup_this" :is-add="false"/>
  </div>
</template>
<script>
import eForm from './form'
export default {
  components: { eForm },
  props: {
    data: {
      type: Object,
      required: true
    },
    sup_this: {
      type: Object,
      required: true
    }
  },
  methods: {
    to() {
      const _this = this.$refs.form
      _this.form = {
<#if columns??>
  <#list columns as column>
        ${column.changeColumnName}: this.data.${column.changeColumnName}<#if column_has_next>,</#if>
  </#list>
</#if>
      }
      _this.dialog = true
    }
  }
}
</script>

<style scoped>
  div{
    display: inline-block;
    margin-right: 3px;
  }
</style>
</script>
<#--start_config-->
#是否有效
enable=true
#文件生成路径
filePath=${frontPath}\\src\\views\\${groupName}\\${firstLowerClassName}\\module\\edit.vue
<#--end_config-->
