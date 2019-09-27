<template>
  <el-dialog :visible.sync="dialog" title="生成器配置" append-to-body width="550px">
    <el-form ref="form" :model="form" :rules="rules" size="small" label-width="78px">
      <el-form-item label="作者名称" prop="author">
        <el-input v-model="form.author" style="width: 420px;"/>
      </el-form-item>
      <el-form-item label="去表前缀" prop="prefix">
        <el-input v-model="form.prefix" placeholder="默认不去除表前缀" style="width: 420px;"/>
      </el-form-item>
      <el-form-item label="分组" prop="groupName">
        <el-input v-model="form.groupName" placeholder="默认采用表前缀" style="width: 420px;"/>
      </el-form-item>
      <el-form-item label="后台路径" prop="moduleName">
        <el-input v-model="form.serverPath" placeholder="默认当前项目" style="width: 420px;"/>
      </el-form-item>
      <el-form-item label="至于包下" prop="pack">
        <el-input v-model="form.pack" style="width: 420px;"/>
      </el-form-item>
      <el-form-item label="前端路径" prop="frontPath">
        <el-input v-model="form.frontPath" style="width: 420px;"/>
      </el-form-item>
      <el-form-item label="是否覆盖" prop="cover">
        <el-radio v-model="form.cover" label="true">是</el-radio>
        <el-radio v-model="form.cover" label="false">否</el-radio>
      </el-form-item>
      <el-form-item label="生成模式" prop="genMode">
        <el-radio v-model="form.genMode" label="admin">后台管理</el-radio>
        <el-radio v-model="form.genMode" label="api">Api接口</el-radio>
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
      <el-form-item label="备注" prop="remark">
        <el-input v-model="form.remark" style="width: 420px;"/>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="text" @click="cancel">取消</el-button>
      <el-button :loading="loading" type="primary" @click="doSubmit">确认</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { getFtlNames, update } from '@/api/genConfig'
export default {
  data() {
    return {
      loading: false, dialog: false,
      form: { author: '', pack: '', frontPath: '', groupName: '', serverPath: '', cover: 'false', genMode: '', remark: '', prefix: '', templates: [] },
      templates: [],
      defaultTemplates: [],
      rules: {
        author: [
          { required: true, message: '作者不能为空', trigger: 'blur' }
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
      var formdata = this.form
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
    resetForm() {
      this.dialog = false
      this.templates = this.defaultTemplates
      this.$refs['form'].resetFields()
      this.form = { author: '', pack: '', frontPath: '', groupName: '', serverPath: '', cover: 'false', genMode: '', remark: '', prefix: '', templates: [] }
    }
  }
}
</script>

<style scoped>

</style>
