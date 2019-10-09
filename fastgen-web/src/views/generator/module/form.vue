<template>
  <div>
    <!--配置-->
    <el-dialog :visible.sync="dialog" title="生成器配置" append-to-body width="550px">
      <el-form ref="form" :model="form" size="small" label-width="78px">
        <el-form-item label="作者名称" prop="author">
          <el-input v-model="form.author" style="width: 420px;"/>
        </el-form-item>
        <el-form-item label="是否覆盖" prop="cover">
          <el-radio v-model="form.cover" label="true">是</el-radio>
          <el-radio v-model="form.cover" label="false">否</el-radio>
        </el-form-item>
        <el-form-item label="模板" prop="serverTemples">
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
              <el-button size="mini" icon="el-icon-delete" @click.prevent="removeComponent(domain)"/>
            </el-col>
          </el-row>
        </el-form-item>
        <el-form-item prop="add">
          <el-button size="mini" icon="el-icon-plus" @click="addComponent" />
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
      title="编辑控件"
      append-to-body
      width="350px">
      <el-form :model="componentForm">
        <el-form-item label="控件名称" prop="componentName">
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
        <el-button @click="componentFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitAddComponent">确 定</el-button>
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
      componentForm: { componentLabel: '', componentName: '', placeholder: '', required: 'false', componentValue: '' },
      templates: [],
      defaultTemplates: [],
      rules: {
        author: [
          { required: true, message: '作者不为空', trigger: 'blur' }
        ],
        pack: [
          { required: true, message: '包路径不能为空', trigger: 'blur' }
        ],
        frontPath: [
          { required: true, message: '前端代码生成路径不能为空', trigger: 'blur' }
        ],
        cover: [
          { required: true, message: '不能为空', trigger: 'blur' }
        ],
        genMode: [
          { required: true, message: '模式不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.initData()
  },
  methods: {
    initData() {
      getFtlNames().then(result => {
        this.templates = result.data
        this.defaultTemplates = result.data
      })
    },
    cancel() {
      this.resetForm()
    },
    doSubmit() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          this.loading = true
          this.doUpdate()
        } else {
          return false
        }
      })
    },
    doUpdate() {
      var formdata = this.getPostData()
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
    getPostData() {
      var postdata = this.form
      this.$set(postdata, 'dynamicForm', this.dynamicForm)
      return postdata
    },
    resetForm() {
      this.dialog = false
      this.templates = this.defaultTemplates
      this.$refs['form'].resetFields()
      this.form = { author: '', pack: '', frontPath: '', groupName: '', serverPath: '', cover: 'false', genMode: '', remark: '', prefix: '', templates: [] }
    },
    // resetForm(formName) {
    //   this.$refs[formName].resetFields();
    // },

    /**
     * 移除控件
     */
    removeComponent(item) {
      var index = this.dynamicForm.indexOf(item)
      if (index !== -1) {
        this.dynamicForm.splice(index, 1)
      }
    },
    /**
     * 新增组件
     */
    addComponent() {
      this.componentForm = { componentLabel: '', componentName: '', placeholder: '', required: 'true' }
      this.componentFormVisible = true
    },
    /**
     * 确认新增组件
     */
    submitAddComponent() {
      this.componentFormVisible = false
      this.dynamicForm.push({
        required: this.componentForm.required,
        componentLabel: this.componentForm.componentLabel,
        componentName: this.componentForm.componentName,
        key: Date.now()
      })
    }
  }
}
</script>

<style scoped>

</style>
