<template>
  <div>
    <!--配置-->
    <el-dialog :visible.sync="dialog" title="配置" append-to-body width="550px">
      <el-form ref="form" :model="form" :rules="formRules" size="small" label-width="78px">
        <el-form-item label="作者名称" prop="author">
          <el-input v-model="form.author" style="width: 420px;"/>
        </el-form-item>
        <el-form-item label="是否覆盖" prop="cover">
          <el-radio v-model="form.cover" label="true">是</el-radio>
          <el-radio v-model="form.cover" label="false">否</el-radio>
        </el-form-item>
        <el-form-item label="模板" prop="templates">
          <el-select v-model="form.templates" multiple filterable placeholder="请选择模板">
            <el-option
              v-for="item in templates"
              :key="item"
              :label="item"
              :value="item"/>
          </el-select>
        </el-form-item>

        <!--动态控件-->
        <el-form-item
          v-for="(domain) in dynamicForm"
          :label="domain.componentLabel"
          :key="domain.key"
          :prop="domain.componentName">
          <el-row>
            <el-col :span="20">
              <el-input v-model="domain.componentValue"/>
            </el-col>
            <el-col :span="2">
              <el-button size="mini" icon="el-icon-setting" @click.prevent="editComponent(true,domain)"/>
            </el-col>
          </el-row>
        </el-form-item>
        <el-form-item prop="add">
          <el-button size="mini" icon="el-icon-plus" @click="editComponent(false,{})" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="text" @click="cancel">取消</el-button>
        <el-button :loading="loading" type="primary" @click="doSubmit()">确认</el-button>
      </div>
    </el-dialog>

    <!--新增组件弹出框-->
    <el-dialog
      :visible.sync="componentFormVisible"
      :title="edited ? '编辑控件' : '新增控件'"
      append-to-body
      width="350px">
      <el-form ref="componentForm" :model="componentForm" :rules="componentRules">
        <el-form-item label="控件名称" prop="componentLabel">
          <el-input v-model="componentForm.componentLabel" placeholder="控件名称" />
        </el-form-item>
        <el-form-item label="控件键值" prop="componentName">
          <el-input v-model="componentForm.componentName" placeholder="控件键值" />
        </el-form-item>
        <el-form-item label="是否必填" prop="required">
          <el-radio v-model="componentForm.required" label="true">是</el-radio>
          <el-radio v-model="componentForm.required" label="false">否</el-radio>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button v-if="edited" @click="removeComponent">删除</el-button>
        <el-button v-if="!edited" @click="cancelComponent">取消</el-button>
        <el-button type="primary" @click="submitEditComponent">确 定</el-button>
      </div>
    </el-dialog>

  </div>

</template>

<script>
import { getFtlNames, update } from '@/api/genConfig'
export default {
  data() {
    return {
      dynamicForm: [],
      loading: false, dialog: false,
      form: { author: '', pack: '', frontPath: '', groupName: '', serverPath: '', cover: 'false', genMode: '', remark: '', prefix: '', templates: [] },
      componentFormVisible: false,
      componentForm: { componentId: '', componentLabel: '', componentName: '', placeholder: '', required: 'false', componentValue: '' },
      edited: false,
      templates: [],
      defaultTemplates: [],
      formRules: {},
      componentRules: {
        componentLabel: [
          { required: true, message: '控件名称不为空', trigger: 'blur' }
        ],
        componentName: [
          { required: true, message: '控件键值不为空', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.initData()
  },
  methods: {
    /**
     * 初始化
     */
    initData() {
      getFtlNames().then(result => {
        this.templates = result.data
        this.defaultTemplates = result.data
      })
    },
    /**
     * 取消
     */
    cancel() {
      this.resetForm()
    },
    /**
     * 提交
     */
    doSubmit() {
      this.handleData()
      this.$refs['form'].validate((valid) => {
        if (valid) {
          this.loading = true
          this.doUpdate()
        } else {
          return false
        }
      })
    },
    /**
     * 更新
     */
    doUpdate() {
      var formdata = this.handleData()
      formdata.templates = formdata.templates.join(',')
      update(formdata).then(res => {
        this.resetForm()
        this.$notify({
          title: '更新成功',
          type: 'success',
          duration: 2500
        })
        this.loading = false
      }).catch(err => {
        this.loading = false
        console.log(err.response.data.message)
      })
    },
    /**
     * 处理动态表单
     */
    handleData() {
      var postdata = this.form
      this.$set(postdata, 'dynamicForm', this.dynamicForm)
      this.dynamicForm.forEach(item => {
        this.$set(postdata, item.componentName, item.componentValue)
      })
      return postdata
    },
    /**
     *重置表单
     */
    resetForm() {
      this.dialog = false
      this.templates = this.defaultTemplates
      this.$refs['form'].resetFields()
      this.form = { author: '', cover: 'false', templates: [] }
    },
    /**
     * 弹出编辑控件
     */
    editComponent(edited, item) {
      this.edited = edited
      this.componentForm = item
      if (!edited) {
        this.componentForm = { componentLabel: '', componentName: '', placeholder: '', required: 'true' }
      }
      this.componentFormVisible = true
    },
    /**
     * 提交编辑控件
     */
    submitEditComponent() {
      if (this.edited) {
        this.updateComponent()
      } else {
        this.saveComponent()
      }
    },
    /**
     * 移除控件
     */
    removeComponent() {
      var index = this.dynamicForm.indexOf(this.componentForm)
      if (index !== -1) {
        this.dynamicForm.splice(index, 1)
      }
      this.componentFormVisible = false
    },
    /**
     * 取消
     */
    cancelComponent() {
      this.componentFormVisible = false
    },
    /**
     * 更新组件
     */
    updateComponent() {
      this.handleData()
      var index = this.dynamicForm.indexOf(this.componentForm)
      this.dynamicForm[index].required = this.componentForm.required
      this.dynamicForm[index].componentLabel = this.componentForm.componentLabel
      this.dynamicForm[index].componentName = this.componentForm.componentName
      this.dynamicForm[index].key = this.dynamicForm[index].key
      this.componentFormVisible = false

      this.formRules = this.getInitRules()
      this.dynamicForm.forEach(item => {
        this.$set(this.formRules, item.componentName, [
          { required: item.required === 'true', message: item.componentLabel + '不能为空', trigger: 'blur' }
        ])
      })
    },
    /**
     * 确认新增组件
     */
    saveComponent() {
      this.$refs['componentForm'].validate((valid) => {
        if (valid) {
          this.componentFormVisible = false
          this.$set(this.formRules, this.componentForm.componentName, [
            { required: this.componentForm.required === 'true', message: this.componentForm.componentLabel + '不能为空', trigger: 'blur' }
          ])
          this.dynamicForm.push({
            required: this.componentForm.required,
            componentLabel: this.componentForm.componentLabel,
            componentName: this.componentForm.componentName,
            key: Date.now()
          })
        } else {
          return false
        }
      })
    },
    /**
     * 获取初始校验数据
     */
    getInitRules() {
      var rules = {
        author: [
          { required: true, message: '作者不能为空', trigger: 'blur' }
        ],
        templates: [
          { required: true, message: '模板不能为空', trigger: 'blur' }
        ],
        cover: [
          { required: true, message: '是否覆盖不能为空', trigger: 'blur' }
        ]
      }
      return rules
    }
  }
}
</script>

<style scoped>

</style>
