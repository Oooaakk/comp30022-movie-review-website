<template>

  <div class="common-layout">
      <el-container>
        <el-header class="header">
         
      <div v-if="routeid===''|| routeid===null">
       <el-button type="warning" round class="LRbutton" @click="$router.push('/moviehub/loginpage')">Login/Register</el-button> 
      </div>
      <div v-else>
        <AvatarIcon :routeID="routeid"/>
      </div>
        </el-header>
        <el-main class="main">
          <el-card class="box-card">
            <div class="title">{{this.$route.query.title}}</div>
              <div>({{this.$route.query.genres}})</div>
          <div><el-image class="image" :src="this.$route.query.poster"></el-image></div>
          <div class="imdb">imdb rating:{{this.$route.query.rating}}</div>
          <div>{{this.$route.query.plot}}</div>
       
          </el-card>
          <div v-if="this.getrating.length===0">
             <span class="noreview">No review yet, share you thoughts!</span>
          </div>
          <div v-else>
            <el-space direction="vertical" class="reviewcard">
              <el-card v-for="(item,index) in getcontent" :key="index" style="width: 830px">

                    <!-- <el-collapse v-model="activeNames" >
                      <el-collapse-item title="see comments" name="1">
                        <div class="content">
                          {{getcontent[index].value}}
                        </div>
                      </el-collapse-item>
                    </el-collapse> -->
                    <div class="content">
                          {{getcontent[index].value}}
                    </div>
                    <el-rate allow-half disabled v-model="getrating[index].value">
                        rating:{{getrating[index].value}}</el-rate>
                    <span class="date">{{getdate[index].value}}</span>
      
              </el-card>
            </el-space>
          </div>
        <div v-if="routeid!==''&& routeid!==null">
        <el-button class="review_button" @click="dialogVisible = true"><el-icon><Plus/></el-icon>Add review</el-button></div>
              <el-dialog
          v-model="dialogVisible"
          title="Post review"
          width="30%"
          :before-close="handleClose"
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
        <el-rate v-model="form.rating" :colors="['#99A9BF', '#F7BA2A', '#FF9900']"
               :max="5"
               allow-half
               show-text
               :texts="['very bad', 'bad', 'normal', 'nice', 'surprise']"></el-rate>
    </el-form-item>
  
    </el-form>
          <template #footer>
            <span class="dialog-footer">
              <el-button @click="dialogVisible = false">Cancel</el-button>
              <el-button type="primary" @click="save()"
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
  import { Plus } from '@element-plus/icons-vue';
  import request from "@/utils/RequestFile.js";
  export default{
    data(){
      var validatePass5 = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('Please input review!'))
        } else {
          callback()
        }
      };
      return{
        activeNames: ['1'],
        // components: {
        //       HubIcon,
        //       AvatarIcon,
        //       Plus,
        // },
        rules: {
          review: [
                { required: true, message: "review cannot be blank", trigger: "blur" },
                { message: "review cannot be blank", trigger: "change" },
                { validator: validatePass5, trigger: "blur" }
          ],
        },
        posterlink:"",
        imdbrating:"",
        movietitle:"",
        dialogVisible:false,
        routeid:JSON.parse(localStorage.getItem('userid')),
        form: {
           movieId:this.$route.params.userID,
           review: "",
           rating:0,
        
        },
        getcontent:[],
        getrating:[],
        getdate:[]
      }
    },
    mounted(){
      this.GetAllreviews();
    },
    methods:{
      // handleClose(done) {
      //   this.$confirm('Are you sure to close this dialog?')
      //     .then()
      // },
      GetAllreviews(){
         request.get("/post/?movieId="+this.$route.params.userID).then(res=>{
           if(res.status===200) {
            for (let i=0;i<res.data.length;i++){
              if(res.data[i].movieId===this.$route.params.userID){
                this.getcontent.push({
                  value:res.data[i].content,
                  label:res.data[i].content
                })
                this.getrating.push({
                  value:res.data[i].rating,
                  label:res.data[i].rating
                })
                this.getdate.push({
                  value:res.data[i].lastModifiedDate.slice(0,10),
                  label:res.data[i].lastModifiedDate.slice(0,10)
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
       
      },
      save() {
          this.$refs['form'].validate((valid) => {
          if (valid) {
              request.post("/post", this.form).then(res => {
                    if (res.status===200) {
                      this.getcontent=[];
                      this.getrating=[];
                      this.GetAllreviews();
                      this.$message({
                        //返回id movie card的
                        type: "success",
                        message: "successfully post the movie"
                      })
                    } else {
                      this.$message({
                        type: "error",
                        message: "fail to post due to unexpected reason"
                      })
                    }
                  })
                  this.dialogVisible = false;
          }else{
            this.$message({
                message: "must complete post first!",
                type: "error",
             })
          }
          })
    },
    
  },
  components:{HubIcon,AvatarIcon,Plus},
  }
  </script>
  <style scoped>
.demo-type {
  display: flex;
}
.demo-type > div {
  flex: 1;
  text-align: center;
}
.text {
  font-size: 14px;
}
.review_button{
  position:fixed;
   top:510px;
   left:1110px;
   color:orange;
}
.item {
  padding: 18px 0;
}
/* .box-card {
  width: 480px;
} */
.demo-type > div:not(:last-child) {
  border-right: 1px solid var(--el-border-color);
}
.header{
  background-color: black;
}
.LRbutton{
  position:relative;
  left:560px;
  top:14px
}
.select{
  margin-left:10px;
  width:10%;
}
.reviewcard{
  position:relative;
  top:-555px;
  left:200px;
}
.recommend{
  position:absolute;
  font-size:x-large;
  font-weight:bold;
}
.box-card{
width: 30%;
height: 550px;
}
.textinput{
   margin-bottom:20px;
}
.image{
 height:300px;
 margin-top: 10px;
 margin-bottom: 10px;
}
.imdb{
  margin-bottom: 10px;
}
.title{
  font-weight: bold;
}
.main{
  background-color: rgb(55, 55, 60);
}
.noreview{
  position:relative;
  top:-550px;
  left:40px;
  font-size:xx-large;
  color:orange;
}
.date{
  position:relative;
  left:340px;
  bottom:-15px
}
.content{
  word-break: break-all;
  word-wrap: break-word;
  overflow:hidden;
}
</style>