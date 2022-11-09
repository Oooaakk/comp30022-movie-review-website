
<template>

  <div class="common-layout">
      <el-container>
        <el-header class="header" >
          
          <AvatarIcon/>
        
        </el-header>
        <span class="pastreview">Past reviews</span>
        <el-main>
          <el-space direction="vertical" class="reviewcard">
              <el-card v-for="(item,index) in getcontent" :key="index" class="box-card" 
              style="width: 830px">
                
                  <div class="card-header" >
                    
                    <span class="content">{{getcontent[index].value}}</span>
                    <el-divider/>
                    <span>rating:{{getrating[index].value}}</span>
                    <el-icon class="editreviewbut" @click="dialogVisible =true"><Edit /></el-icon>
                    <el-icon class="deletereviewbut"><Delete /></el-icon>
                  </div>
              </el-card>
            </el-space>
            <el-dialog
              v-model="dialogVisible"
              title="Edit review"
              width="30%"

            >
            <el-form
            :model="form"
            status-icon
            :rules="rules"
            ref="form"
            label-position="left"
            label-width="100px"
            class="login-from"
            hide-required-aterisk="true">
            
            <el-form-item label = "Review: " class="labelcolor">
            <el-input
            v-model="form.review"
            :rows="3"
            type="textarea"
            placeholder="Please input movie review"
            class="textinput"
            
            />
          </el-form-item>
          <el-form-item class = "rating" label="Rating" prop="rating">
            <el-select v-model="form.rating" placeholder="enter your rating">
              <el-option
                        v-for="item in rating_list"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                      </el-option>
              </el-select>
        </el-form-item>
      
        </el-form>
              <template #footer>
                <span class="dialog-footer">
                  <el-button @click="dialogVisible = false">Cancel</el-button>
                  <el-button type="primary" @click="dialogVisible = false"
                    >Confirm</el-button
                  >
                </span>
              </template>
            </el-dialog>
        </el-main>
    
  </el-container>
      
  </div>
    
   <HubIcon/>
  </template>


  <script>
    import HubIcon from '@/components/HubIcon.vue';
    import AvatarIcon from '@/components/AvatarIcon.vue';
    import request from "@/utils/RequestFile.js";
    import {Edit,Delete} from "@element-plus/icons-vue"
    export default{
      data(){
        return{
          getcontent:[],
          getrating:[],
          dialogVisible :false,
          rating_list: [{
            value: '0',
            label: '0'
          }, {
            value: '1',
            label: '1'
          }, {
            value: '2',
            label: '2'
          }, {
            value: '3',
            label: '3'
          }, {
            value: '4',
            label: '4'
          }, {
            value: '5',
            label: '5'
          }
        ],
        form: {
           review: "",
           rating:"",
        
        },
        rules: {
          review: [
                { required: true, message: "review cannot be blank", trigger: "blur" },
                { message: "review cannot be blank", trigger: "change" },
          ],
          rating: [
                { required: true, message: "rating cannot be blank", trigger: "blur" },
                {  message: "rating cannot be blank", trigger: "change" },
          ],
         },
        }
      },
      
      mounted(){
         this.getpastreviews()
      },
      methods:{
        getpastreviews(){
         request.get("/post/?userId="+this.$route.params.userID).then(res=>{
           if(res.status===200) {
            for (let i=0;i<res.data.length;i++){
              if(res.data[i].userId===this.$route.params.userID){
                this.getcontent.push({
                  value:res.data[i].content,
                  label:res.data[i].content
                })
                this.getrating.push({
                  value:res.data[i].rating,
                  label:res.data[i].rating
                })
              }
            }
            
           }else{
            this.$message({
              type: "error",
              message: "fail to get due to unexpected reason"
             })
            }
          })
       
      }
      },
      components: { HubIcon,AvatarIcon,Edit,Delete}
    }
    
    </script>

<style>
 
body {
  background-color: #222231;
}
.header{
  background-color: black;
}
.main{
  background-color: white;
  height:64px;
  
}
.content{
  word-break: break-all;
  word-wrap: break-word;
  overflow:hidden;
}
.pastreview{
  color:orange;
  font-size:xx-large;
  margin-top:20px;
}
.box_card{
  cursor:pointer;
}
.editreviewbut{
  position:relative;
  left:200px;
  bottom:-20px;
  width:120%;
  cursor:pointer;
}
.deletereviewbut{
  position:relative;
  left:350px;
  cursor:pointer
}
</style>
