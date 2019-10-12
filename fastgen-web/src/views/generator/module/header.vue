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
    <div class="right-item" >
      <el-select v-model="currencyProjectId" @change="switchProject" style="width: 110px; margin-right:3px;" filterable clearable  placeholder="请选择">
        <el-option
          v-for="item in projects"
          :key="item.path"
          :label="item.projectName"
          :value="item.projectId">
          <span style="float: right">
            {{ item.projectName }} <i class="el-icon-edit" @click="editProject(item)"></i>
          </span>
        </el-option>
      </el-select>
      <i class="el-icon-plus" @click="createProject"></i>
    </div>

    <!--新增项目-->
    <el-dialog
      :visible.sync="projectFormVisible"
      :title="edited ? '编辑项目' : '新增项目'"
      append-to-body
      width="350px">
      <el-form ref="projectForm" :model="projectForm" :rules="projectRules">
        <el-input v-model="projectForm.projectId" type="hidden" />
        <el-form-item label="项目名称" prop="projectName">
          <el-input v-model="projectForm.projectName" placeholder="项目名称"/>
        </el-form-item>
        <el-form-item label="项目路径" prop="path">
          <el-input v-model="projectForm.path" placeholder="项目路径"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button v-if="edited" @click="removeProject">删除</el-button>
        <el-button v-if="!edited" @click="projectFormVisible = false">取消</el-button>
        <el-button type="primary" @click="submitEditProject">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import { get ,update } from '@/api/genConfig'
import eForm from './form'
// 查询条件
export default {
  components: { eForm },
  data() {
    return {
      projects: [],
      configs: { projects: [] },
      currencyProjectId: '',
      projectFormVisible : false,
      projectForm: { projectId: '', projectName: '', path: '' },
      projectRules: {
        projectName: [
          {required: true, message: '项目名不能为空', trigger: 'blur'}
        ],
        path: [
          {required: true, message: '项目路径不能为空', trigger: 'blur'}
        ]
      },
      edited: false
    }
  },
  props: {
    query: {
      type: Object,
      required: true
    }
  },
  created() {
    this.initData()
  },
  methods: {
    initData() {
      get().then(res => {
        var data = res.data
        this.configs = data
        this.projects = data.projects == undefined ? [] : data.projects
      })
    },
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

        _this.formRules = _this.getInitRules()
        _this.dynamicForm.forEach(item => {
          this.$set(_this.formRules, item.componentName, [
            { required: item.required === 'true', message: item.componentLabel + '不能为空', trigger: 'blur' }
          ])
        })
      })
      _this.dialog = true
    },

    /**
     * 切换项目
     */
    switchProject(item) {
      debugger
      this.currencyProjectId = item
    },
    /**
     * 删除项目
     */
    removeProject() {
      var index = this.projects.indexOf(this.projectForm)
      if (index !== -1) {
        this.projects.splice(index, 1)
      }
      this.projectFormVisible = false
      var newConfigs = this.configs
      newConfigs.projects = this.projects
      update(newConfigs).then(res => {
        this.$notify({
          title: '删除成功',
          type: 'success',
          duration: 1000
        })
      })
    },
    /**
     * 编辑项目
     */
    editProject(item) {
      this.edited = true
      this.projectFormVisible = true;
      this.projectForm = item
    },
    /**
     * 新增项目
     */
    createProject() {
      this.edited = false
      this.projectFormVisible = true;
      this.projectForm = { projectId: Date.now(), projectName: '', path: '' }
    },
    /**
     * 确认新增项目
     */
    submitEditProject() {
      this.$refs['projectForm'].validate((valid) => {
        if (valid) {
          this.projectFormVisible = false
          if(!this.edited){
            this.projects.push(this.projectForm)
          }else {
            var index = this.projects.indexOf(this.projectForm)
            if (index !== -1) {
              this.projects[index]=this.projectForm;
            }
          }
          var newConfigs = this.configs
          newConfigs.projects = this.projects
          update(newConfigs).then(res => {
            this.$notify({
              title: '操作成功',
              type: 'success',
              duration: 1000
            })
          })
        } else {
          return false
        }
      })
    }
  }
}
</script>


<style scoped>
  .right-item{
    float:right;
    border: none;
    padding-right: 20px;
  }
</style>
