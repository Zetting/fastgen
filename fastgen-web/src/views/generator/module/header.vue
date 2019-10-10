<template>
  <div class="head-container">
    <el-input v-model="query.keywords" clearable placeholder="请输入关键字" style="width: 200px;" class="filter-item" @keyup.enter.native="toQuery"/>
    <el-button class="filter-item" size="mini" type="success" icon="el-icon-search" @click="toQuery">搜索</el-button>
    <!-- 新增 -->
    <div style="display: inline-block;margin: 0px 2px;">
      <el-button
        class="filter-item"
        size="mini"
        type="warning"
        icon="el-icon-setting"
        @click="to">配置</el-button>
      <eForm ref="form"/>
    </div>
  </div>
</template>

<script>
import { get } from '@/api/genConfig'
import eForm from './form'
// 查询条件
export default {
  components: { eForm },
  props: {
    query: {
      type: Object,
      required: true
    }
  },
  methods: {
    toQuery() {
      this.$parent.page = 0
      this.$parent.init()
    },
    to() {
      const _this = this.$refs.form
      get().then(res => {
        var data = res.data
        _this.form = data
        _this.dynamicForm = data.dynamicForm
        _this.form.templates = data.templates == null || data.templates === '' ? [] : data.templates.split(',')
        _this.form.cover = _this.form.cover.toString()
        _this.formRules = this.getInitRules()
        _this.dynamicForm.forEach(item => {
          this.$set(_this.formRules, item.componentName, [
            { required: item.required === 'true', message: item.componentLabel + '不能为空', trigger: 'blur' }
          ])
        })
      })
      _this.dialog = true
    }
  }
}
</script>
