<template>
  <el-popover
    :width="200"
    trigger="hover"
    content=""
    
  >
  <div class="personalnfo"> 
    <div>{{this.usernamerender}}  age:{{this.agerender}}
      <div  v-if="this.genederrender==='MALE'">
      <img class="maleimg" src="../assets/3233508.png"/></div>
    <div v-else-if="this.genederrender==='FEMALE'">
      <img class="femaleimg" src="../assets/3233515.png"/>
    </div>
    </div>
  </div>
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
      <div class="avatar1" >
        <el-avatar 
        
        @click="$router.push('/moviehub/dashboard/'+rID)"
          ><!--头像--><img :src=this.imageUrl /></el-avatar>
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
        :model="ruleForm"
  
        ref="ruleForm"
        :rules="rules"
        status-icon
        >
          
        <el-form-item class = "languagecolour1" label="Original Password" prop="oripass">
      <el-input
        class = "inputform"
        v-model="ruleForm.oripass"
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
        <el-button type="primary" @click="dialogVisible = false;handleClose('ruleForm')" 
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
        <el-button type="primary" @click="dialogVisibleSetting  = false;handleClose2()"
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
        ref="doctypeCrfile"
    v-model:file-list="fileList"
    class="upload-demo"
    action="https://moivehub-itproject-team004.herokuapp.com/photo"
    :on-preview="handlePreview"
    :before-remove="beforeRemove"
    :limit="1"
    name="image"
    :on-exceed="handleExceed"
    :auto-upload="false"
    :headers="this.h"
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

<script>
import { InfoFilled } from '@element-plus/icons-vue'
import { SwitchButton } from '@element-plus/icons-vue'
import { Setting } from '@element-plus/icons-vue'
import { Lock } from '@element-plus/icons-vue'
import { Avatar } from '@element-plus/icons-vue'
import {axios} from 'axios';
import request from '@/utils/RequestFile.js'
import { useRouter } from 'vue-router';

export default{
  data(){
    var validatePass1 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('Please input the password'))
        }else if(value!=='' && ((this.ruleForm.oripass.length < 5 || this.ruleForm.oripass.length > 16))){
          console.log("incorrect password form!!")
          callback(new Error('Please input correct form of password'))
        }else{
          callback()
        }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('Please input the password'))
        }else if(value!=='' && ((this.ruleForm.checkPass.length < 5 || this.ruleForm.checkPass.length > 16))){
          console.log("incorrect password form!!")
          callback(new Error('Please input correct form of password'))
        }else{
          callback()
        }
    };
    var validatePass3 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('Please input the password again'))
      } else if (value !== this.ruleForm.checkPass) {
        callback(new Error("Two inputs don't match!"))
      } else {
        callback()
      }
    }
    return{
      h:{Authorization:'Bearer ' + JSON.parse(localStorage.getItem("user"))},
      imageUrl:'',
      file:'',
      fileList: [],
      value:'',
      age:'',
      input:'',
      usernamerender:'',
      agerender:'',
      genederrender:'',
      dialogVisible:false,
      dialogVisibleSetting :false,
      dialogVisibleAvatar:false,
      rID:JSON.parse(localStorage.getItem('userid')),
      ruleForm :{
          oripass:"",
          checkPass: "",
          confirmPass: "",

      },
      options:[
        {
          value: 'male',
          label: 'male',
        },
        {
          value: 'female',
          label: 'female',
        },
      
      ],
      rules: {
        oripass:[{ validator: validatePass1, trigger: 'blur' }],
        checkPass: [{ validator: validatePass2, trigger: 'blur' }],
        confirmPass: [{ validator: validatePass3, rigger: 'blur' }],
        
      },
     };
     

   },
   mounted(){
    if (localStorage.getItem("userid")!==null && localStorage.getItem("userid")!==undefined){
     this.getuserinfo()
     
     this.getavatar()
    }
   },
   methods: {
     getuserinfo(){
         request.get("/user/info/userId="+this.rID).then(res=>{
          if (res.status===200){
             this.usernamerender=res.data.body.username
             this.agerender=res.data.body.age,
             this.genederrender=res.data.body.gender
          }
         })
     },
     getavatar(){
        request.get("/photo/userId="+this.rID, {responseType: "blob"}).then(res=>{
          if (res.status===200){
            const fileReader = new FileReader()
                fileReader.readAsDataURL(res.data)
                fileReader.onload = e => {
                  this.imageUrl = e.target.result
            }
          }
        })
     },
     arrayBufferToBase64 (buffer) {
        var binary = ''
        var bytes = new Uint8Array(buffer)
        var len = bytes.byteLength
        for (var i = 0; i < len; i++) {
          binary += String.fromCharCode(bytes[i])
        }
        return window.btoa(binary)
      },
      handlePreview(file) {
        console.log(file);
      },
      handleExceed(files, fileList) {
        this.$message.warning(`The limit is 1, you selected ${files.length}
         files this time, add up to ${files.length + fileList.length} totally`);
      },
      beforeRemove(file, fileList) {
        return this.$confirm(`Cancel the transfert of ${ file.name } ?`);
      },
      logout(){
        window.localStorage.clear();
        useRouter().push('/moviehub/loginpage');
      },
      handleClose(formName){
        this.$refs[formName].validate((valid)=> {
          if (valid && this.confirmPass===this.checkPass){
            request.post('/user/changePass',{oldPass:this.ruleForm.oripass,newPass:this.ruleForm.checkPass}).then((res)=>{
              if (res.status===200){
                this.$message({
                    message: "change password successfully",
                    type: "success",
                });
              }else{
                this.$message({
                    message: "fail to change password ",
                    type: "error",
                });
              }
            })
          }else{
            this.dialogVisible=true;
            this.$message({
                    message: "fill the form first! ",
                    type: "error",
             });
          }
        })
      },
      handleClose2(){
         if (this.input!==''&& this.value!==""&&this.age!==""){
            request.post('/user/changeSettings',{age:this.age,gender:this.value,username:this.input}).then((res)=>{
                  if (res.status===200){
                    this.$message({
                        message: "change settings successfully!",
                        type: "success",
                    });
                    this.$router.go(0)
                  }else{
                    this.$message({
                        message: "fail to change settings!",
                        type: "error",
                    });
                  }
            })
          }
          else{
            this.dialogVisibleSetting  = true;
            this.$message({
                  message: "must fill the form before submit!",
                  type: "error",
            });
          }
      }
      ,
      handleClose3(){
         this.$refs.doctypeCrfile.submit();
         
      }
    },
    props:{
      routeID:String
    },
    components:{  SwitchButton ,Setting,Lock,Avatar}
}

</script>
<style>

  .avatar1{
  position:absolute;
  top:10px;
  left:96%
}
.avatar1:hover{
  cursor:pointer;
  -webkit-transform: translateY(-3px);
  -ms-transform: translateY(-3px);
  transform: translateY(-3px);
  -webkit-box-shadow: 0 0 6px #999;
  box-shadow: 0 0 6px #999;
  -webkit-transition: all .5s ease-out;
  transition: all .5s ease-out;
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
.personalnfo{
    color:orange;
    position:relative;
    left:50px;

  }

  .maleimg{

    width:16%;
    height:16%;
    position:relative;
    left:22px;

  }
  .femaleimg{
    width:16%;
    height:16%;
    position:relative;
    left:22px;
  }
</style>
