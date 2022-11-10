
<template>

  <div class="common-layout">
      <el-container>
        <el-header class="header" >
          
          <AvatarIcon/>
      
        </el-header>
        
        <el-form class="allform"
      
        >
          
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
        <!-- <el-form-item class = "languagecolour" label="Age">
    <el-input-number class="ageoption" style = "width: 300px" v-model="age" :min="1" :max="120" @change="handleChange" />
  </el-form-item> -->

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
      
      
    </el-form>
    <el-button type="warning" round class="LRbutton" @click="$router.push('/moviehub/dashboard/1234')">Save</el-button> 
  </el-container>
      
    </div>
    
   <HubIcon/>
  </template>


  <script lang="ts" setup>
    import { ElMessage, ElMessageBox } from 'element-plus'
    import { ref } from 'vue'
import type { UploadProps, UploadUserFile } from 'element-plus'
import HubIcon from '@/components/HubIcon.vue';
import AvatarIcon from '../components/AvatarIcon.vue';

const input = ref('')
const value = ref()

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

    </script>

<style>
.languagecolour {
  color: #FF9900;
  margin-left:330px;
  margin-top:40px;
}
body {
background-color: #222231;
}
.header{
  background-color: black;

}

.LRbutton{
  width:10%;
  margin-left:590px;
}
.main{
  background-color: white;
  height:64px;
  
}
.upload-demo{
  margin-left:-50px;
}
.movietitle{
   margin-top:50px;
   margin-left:330px;

}
.labelcolor{
   margin-top:50px;
   margin-left:330px;

}
.textinput{
  margin-left:17px;
  width:50%;
 
}
.star{
  margin-left:24px;
}

.movietitle .el-form-item__label {
  color: orange
}

.labelcolor .el-form-item__label {
  color: orange
}
.languagecolour .el-form-item__label {
  color: orange
}

.movietitleinput{
  width:50%;
}
.genderoption{
  margin-left:17px;
}

.ageoption{
  margin-left: 42px;
}
.allform{
  margin-left:100px;
}
.inputform{
  width:50%;
  margin-left: 1px;
}
</style>