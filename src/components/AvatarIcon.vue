<template>
  <el-popover
    :width="200"
    trigger="hover"
    content=""
    
  >
  <el-button @click="dialogVisible = true" class="cpass">
      <el-icon class="setting"><Lock /></el-icon>Change password</el-button>

    <el-button @click="dialogVisibleSetting  = true" class="setting">
      <el-icon class="setting"><Setting /></el-icon>SettingPage</el-button>

      <el-button @click="dialogVisibleAvatar  = true" >
      <el-icon class="setting"><Avatar /></el-icon>Change Avatar</el-button>
      
    <el-popconfirm
    confirm-button-text="Yes"
    cancel-button-text="No"
    :icon="InfoFilled"
    icon-color="#626AEF"
    title="Are you sure to log out?"
    @confirm="$router.push('/moviehub/loginpage')"
  >
  <template #reference>
      <el-button class="logoutbutton" @click="logout()"><el-icon class="Sbutton">
          <SwitchButton /></el-icon>logout</el-button>
    </template>
  </el-popconfirm>
  
    <template #reference>
      <div class="avatar" >
        <el-avatar 
        src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
        @click="$router.push('/moviehub/dashboard/'+props.routeID)"
          /><!--头像-->
    </div>
    </template>
  </el-popover>

  <!--password-->
  <el-dialog
    v-model="dialogVisible"
    title="Change Password"
    width="30%"
  >
  <el-form class="allform"
        ref="ruleFormRef"
        :rules="rules"
        status-icon
        >
          
        <el-form-item class = "languagecolour1" label="Original Password" prop="oripass">
      <el-input
        class = "inputform"
        v-model="ruleForm.checkPass"
        type="password"
        autocomplete="off"
      />
    </el-form-item>
  <el-form-item class = "languagecolour1" label="New Password" prop="checkPass">
      <el-input
        class = "inputform"
        v-model="ruleForm.checkPass"
        type="password"
        autocomplete="off"
      />
    </el-form-item>
    <el-form-item class = "languagecolour2" label="Confirm Password" prop="confirmPass">
      <el-input  class = "inputform" v-model="ruleForm.confirmPass" type="Password" autocomplete="off"/>
    </el-form-item>
    
      
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">Cancel</el-button>
        <el-button type="primary" @click="dialogVisible = false;handleClose(ruleFormRef)" 
          >Confirm</el-button
        >
      </span>
    </template>
  </el-dialog>

  <!--Setting-->
  <el-dialog
    v-model="dialogVisibleSetting"
    title="Change Settings"
    width="30%"
  >
    <el-form class="allform">
        <el-form-item label = "Username: " class="movietitle">
          <el-input class="movietitleinput" style = "width: 300px" v-model="input" placeholder="Please input new username" />
        </el-form-item>
        <el-form-item label="Gender: " class = "languagecolour">
          <el-select class="genderoption" style = "width: 300px" v-model="value" placeholder="Select" size="large">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item class = "languagecolour" label="Age">
    <el-input-number class="ageoption" style = "width: 300px" v-model="age" :min="1" :max="120" />
  </el-form-item>

     
    </el-form>

    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisibleSetting  = false">Cancel</el-button>
        <el-button type="primary" @click="dialogVisibleSetting  = false;handleClose2(ruleFormRef)"
          >Confirm</el-button
        >
      </span>
    </template>
  </el-dialog>
  <!--头像-->
  <el-dialog
    v-model="dialogVisibleAvatar"
    title="Upload Avatar"
    width="30%"
 
  >
  <el-form-item label="Add avatar:" class="labelcolor">
        <el-upload
    v-model:file-list="fileList"
    class="upload-demo"
    action="https://run.mocky.io/v3/9d059bf9-4660-45f2-925d-ce80ad6c4d15"
    multiple
    :on-preview="handlePreview"
    :on-remove="handleRemove"
    :before-remove="beforeRemove"
    :limit="1"
    :on-exceed="handleExceed"
    :auto-upload="false"
    
  >
    <el-button type="primary">Click to upload</el-button>
    <template #tip>
      <div class="el-upload__tip">
        jpg/png files with a size less than 500KB.
      </div>
    </template>
  </el-upload>
      </el-form-item> 
      
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisibleAvatar = false">Cancel</el-button>
        <el-button type="primary" @click="dialogVisibleAvatar = false;handleClose3()"
          >Confirm</el-button
        >
      </span>
    </template>
  </el-dialog>
</template>
<script lang="ts" setup>
import { InfoFilled } from '@element-plus/icons-vue'
import { SwitchButton } from '@element-plus/icons-vue'
import { Setting } from '@element-plus/icons-vue'
import { Lock } from '@element-plus/icons-vue'
import { Avatar } from '@element-plus/icons-vue'
import { reactive,ref,defineProps  } from 'vue'
import type { FormInstance } from 'element-plus'
import request from '@/utils/RequestFile'
import { ElMessage, ElMessageBox } from 'element-plus'
import type { UploadProps, UploadUserFile } from 'element-plus'
import { useRouter } from 'vue-router'
const input = ref('')
const value = ref()
const age = ref(1)
//const routeiD=ref(JSON.parse(localStorage.getItem('userid')))
const fileList = ref<UploadUserFile[]>([
])
const options = [
  {
    value: '0',
    label: 'Male',
  },
  {
    value: '1',
    label: 'Female',
  },
  
]
const props = defineProps ({
		routeID:String
})
const logout=()=>{
  window.localStorage.clear();
  useRouter().push('/moviehub/loginpage');
}
const handleRemove: UploadProps['onRemove'] = (file, uploadFiles) => {
  console.log(file, uploadFiles)
}
const handlePreview: UploadProps['onPreview'] = (uploadFile) => {
  console.log(uploadFile)
}
const handleExceed: UploadProps['onExceed'] = (files, uploadFiles) => {
  ElMessage.warning(
    `The limit is 1, you selected ${files.length} files this time, add up to ${
      files.length + uploadFiles.length
    } totally`
  )
}
const beforeRemove: UploadProps['beforeRemove'] = (uploadFile) => {
  return ElMessageBox.confirm(
    `Cancel the transfert of ${uploadFile.name} ?`
  ).then(
    () => true,
    () => false
  )
}
const ruleFormRef = ref<FormInstance>()
  const validatePass1 = (rule: any, value: any, callback: any) => {
  if (value === '') {
    callback(new Error('Please input the password'))
  } else {
    if (value!='' && ((ruleForm.checkPass.length < 8 || ruleForm.checkPass.length > 16))){
      callback(new Error('Please input correct form of password'))
    }
    if (ruleForm.confirmPass !== '') {
      if (!ruleFormRef.value) return
      ruleFormRef.value.validateField('confirmPass', () => null)
    }
    callback()
  }
}
const validatePass2 = (rule: any, value: any, callback: any) => {
  if (value === '') {
    callback(new Error('Please input the password'))
  } else {
    if (value!='' && ((ruleForm.checkPass.length < 8 || ruleForm.checkPass.length > 16))){
      callback(new Error('Please input correct form of password'))
    }
    if (ruleForm.confirmPass !== '') {
      if (!ruleFormRef.value) return
      ruleFormRef.value.validateField('confirmPass', () => null)
    }
    callback()
  }
}
const validatePass3 = (rule: any, value: any, callback: any) => {
  if (value === '') {
    callback(new Error('Please input the password again'))
  } else if (value !== ruleForm.checkPass) {
    callback(new Error("Two inputs don't match!"))
  } else {
    callback()
  }
}
const rules = reactive({
  oripass:[{ validator: validatePass1, trigger: 'blur' }],
  checkPass: [{ validator: validatePass2, trigger: 'blur' }],
  confirmPass: [{ validator: validatePass3, rigger: 'blur' }],
  
})
const ruleForm = reactive({
  oripass:'',
  checkPass: '',
  confirmPass: '',
  
})
const dialogVisible = ref(false)
const dialogVisibleSetting = ref(false)
const dialogVisibleAvatar = ref(false)
const handleClose = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.validate((valid) => {
    if (valid) {
      request.put
    } else {
      console.log('error submit!')
      return false
    }
  })
}
const handleClose2 = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.validate((valid) => {
    if (valid) {
      request.put
    } else {
      console.log('error submit!')
      return false
    }
  })
}
const handleClose3 = () => {
  console.log('error submit!')
}
  </script>
<style>
  .avatar{
  position:fixed;
  top:10px;
  left:1210px
}
.avatar:hover{
  cursor:pointer;
}
.setting{
    margin-top:10px
}
.cpass{
    margin-left:11.5px
}
.lock{
  margin-right:5px;
}
.setting{
  margin-right:5px;
  margin-bottom: 10px;
}
.plus{
  margin-right:5px;
}
.Sbutton{
  margin-right: 5px;
}
.logoutbutton{
  margin-top: 10px;
}
</style>
