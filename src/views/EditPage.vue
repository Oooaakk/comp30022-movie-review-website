
<template>

  <div class="common-layout">
      <el-container>
        <el-header class="header">
          <AvatarIcon/>
        </el-header>
        <el-main class="main">

        <div class="edithere">Edit here</div>  
        </el-main>
        <el-form>
        
        <el-form-item label = "Review: " class="labelcolor">
        <el-input
        v-model="textarea"
        :rows="2"
         type="textarea"
        placeholder="Please input movie review"
        class="textinput"
        
        />
      </el-form-item>
        <el-form-item label = "Rating: " class="labelcolor">
        <el-rate
          v-model="value"
          :texts="['oops', 'disappointed', 'normal', 'good', 'great']"
          text-color = 'orange'
          show-text
          class="star"
        ></el-rate>
        
      </el-form-item>
      <el-form-item label="Add poster: " class="labelcolor">
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
    import { ref } from 'vue'
    import { ElMessage, ElMessageBox } from 'element-plus'

import type { UploadProps, UploadUserFile } from 'element-plus'
import HubIcon from '@/components/HubIcon.vue';
import AvatarIcon from '@/components/AvatarIcon.vue';
const textarea = ref('')
const value = ref()

const fileList = ref<UploadUserFile[]>([

])

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
  body {

background-color: #222231;
}
  .header{
  background-color: black;

  }
  
.LRbutton{
  width:10%;
  margin-left:530px;
}
.main{
  background-color: white;
  height:64px;
  
}
.upload-demo{
  margin-left:-55px;
}
.edithere{
  position: absolute;
  font-weight: bold;
  font-size: x-large;
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

.labelcolor .el-form-item__label {
  color: orange
}

</style>