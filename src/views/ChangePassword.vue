<template>


  <div class="common-layout">
      <el-container>
        <el-header class="header" >
          
          <AvatarIcon/>
      
        </el-header>
        
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
    <el-button type="warning" round class="LRbutton" @click="$router.push('/moviehub/dashboard/1234')">Save</el-button> 
  </el-container>
      
    </div>
    
   <HubIcon/>
  </template>


  <script lang="ts" setup>
    import { reactive, ref } from 'vue'
import type { FormInstance } from 'element-plus'
import HubIcon from '@/components/HubIcon.vue';
import AvatarIcon from '@/components/AvatarIcon.vue';
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
// const submitForm = (formEl: FormInstance | undefined) => {
//   if (!formEl) return
//   formEl.validate((valid) => {
//     if (valid) {
//       console.log('submit!')
//     } else {
//       console.log('error submit!')
//       return false
//     }
//   })
// }

    </script>

<style>

body {
  background-color: #222231;
}
.header{
  background-color: black;

}

.LRbutton{
  width:10%;
  margin-left:590px;
  margin-top:30px;
}
.main{
  background-color: white;
  height:64px;
  
}
.languagecolour1{
   margin-top:150px;
   margin-left:270px;

}
.languagecolour2{
   margin-top:40px;
   margin-left:250px;

}
.textinput{
  margin-left:17px;
  width:50%;
 
}

.labelcolor .el-form-item__label {
  color: orange
}
.languagecolour1 .el-form-item__label {
  color: orange
}
.languagecolour2 .el-form-item__label {
  color: orange
}

.allform{
  margin-left:100px;
}
.inputform{
  width:50%;
  margin-left: 1px;
}
</style>