<template>

  <div class="common-layout">
    <el-container>
      <el-header class="header">
        <div class="seaerchthreegroup">
            <el-autocomplete 
 
              placeholder="search your movie here!" 
              popper-class="my-autocomplete" 
              class="input-with-select" 
              v-model="keywords" 
              :fetch-suggestions="querySearchAsync" 
              :trigger-on-focus="false" 
              @select="handleSelect">
            </el-autocomplete>

      </div>
      
      <div v-if="routeuserID===''|| routeuserID===null">
        <el-popover
          placement="top-start"
          title="Notice"
          :width="200"
          trigger="hover"
          content="after login you can post review, like review, see your own past reviews, post your avatar and much more!"
        >
          <template #reference>
            <el-button type="warning" round class="LRbutton" @click="$router.push('/moviehub/loginpage')">Login/Register</el-button> 
          </template>
        </el-popover>
       
      </div>
      <div v-else>
        <AvatarIcon :routeID="routeuserID"/>
        
      </div>

  
      </el-header>
      
      <el-main class="main">
        <div class="nowgroup">
        <div class="nowplayingtext">Now playing</div>
        <div class="nowplayingcarousel">
        <el-carousel height="330px" indicator-position="none" >
      <el-carousel-item v-for="(item,i) in nowimg" :key="i" >

        <div ><el-image class="nowplayingimg" :src="nowimg[i].value"></el-image></div>
      </el-carousel-item>
    </el-carousel>
  </div>
  </div>
    <el-divider/>
        <div class="recommend">Recommend movies</div>
        <div class="filteroptiongroup">
        <el-select v-model="value2" class="select" placeholder="sortBy">
    <el-option
      v-for="item in optionsSort"
      :key="item.value"
      :label="item.label"
      :value="item.value"
      
    />
  </el-select>
  
  <el-select v-model="value3" class="select" placeholder="filter">
    <el-option
      v-for="item in options"
      :key="item.value"
      :label="item.label"
      :value="item.value"
      
    />
  </el-select>
  
  <el-button class= "recbutton" @click="searchrelmovie()">recommended</el-button>
  <el-button @click="getTopmovies()">Top 250</el-button>
  <el-button @click="getratedmovies()">Rated movies</el-button>
</div>
  
        <el-divider />

        <div v-if="this.ourrank===false">
        <el-space wrap>
          
            <el-card v-for="(item,i) in movietitle" :key="i" class="box-card" 
             style="width: 250px;margin-right:20px"
             @click="jumptoContent(i)">

                <div class="card-header">
                  <span>{{movietitle[i].value}}({{moviegenre[i].value}})</span>
                  <el-divider/>
  
                  <span><el-image :src="movieposter[i].value"></el-image></span>
                  <el-divider/>
                  <span>imdb rating:{{movieofficialrating[i].value}}</span>
                  <div>our rating:<el-rate allow-half disabled v-model="avgrating[i]">
                        {{this.avgrating[i]}}</el-rate></div>
                </div>
            </el-card>
          
        </el-space>
      </div>
      <div v-else>
        <el-space wrap>
          
          <el-card v-for="(item,i) in ourranktitle" :key="i" class="box-card" 
           style="width: 250px;margin-right:20px">

              <div class="card-header">
                <span>{{ourranktitle[i]}}</span>
                <el-divider/>
                <span><el-image :src="ourrankposter[i]"></el-image></span>
                <el-divider/>
                <div>our rating:<el-rate allow-half disabled v-model="avgratingrank[i]">
                      {{this.avgratingrank[i]}}</el-rate></div>
              </div>
          </el-card>
        
      </el-space>
      </div>
        <el-backtop :bottom="100">
          <div
            style="
              height: 100%;
              width: 100%;
              background-color: var(--el-bg-color-overlay);
              box-shadow: var(--el-box-shadow-lighter);
              text-align: center;
              line-height: 40px;
              color: #1989fa;
            "
          >
            UP
          </div>
        </el-backtop>
      </el-main>
      
    </el-container>
    
  </div>
  
  <HubIcon/>
  </template>
  
  <script>
  import HubIcon from '@/components/HubIcon.vue';
  import AvatarIcon from '@/components/AvatarIcon.vue';
  import request2 from "@/utils/Request2.js";
  import request from "@/utils/RequestFile.js"
  export default{
      data(){
          return{
            keywords: "",
            input1:'',
            value3:'',
            value2:'',
            options : [
            {
              value: 'Drama',
              label: 'Drama',
            },
            {
              value: 'Comedy',
              label: 'Comedy',
            },
            {
              value: 'Horror',
              label: 'Horror',
            },
            {
              value: 'Thriller',
              label: 'Thriller',
            },
            {
              value: 'Action',
              label: 'Action',
            },
            {
              value: 'Sci-Fi',
              label: 'Sci-Fi',
            },
            {
              value: 'Crime',
              label: 'Crime',
            },
            {
              value: 'Adventure',
              label: 'Adventure',
            },
          
            {
              value: 'Other',
              label: 'Other',
            }
            ],
            optionsSort: [
            {
              label: 'A-Z Ascending',
              value: 'alpha,asc',
            },
            {
              label: 'A-Z Descending',
              value: 'alpha,desc',
            },
            {
              label: 'User Rating Ascending',
              value: 'user_rating,asc',
            },
            {
              label: 'User Rating Descending',
              value: 'user_rating,desc',
            },
            {
              label: 'Year Descending',
              value: 'year,desc',
            },
            {
              label: 'Year Ascending',
              value: 'year,asc',
            },
            {
              label: 'Popularity Ascending',
              value: 'moviemeter,asc',
            },
            {
              label: 'Popularity Descending',
              value: 'moviemeter,desc',
            },
            
            ],
            movietitle:[],
            movieposter:[],
            moviegenre:[],
            movieofficialrating:[],
            movieid:[],
  
            itemlist: [],
            //postlist:[],
            movielist : [],
            movieplot:[],
            inputText: "",
            poster: "",
            rating: "",
            title: "",
            usernamerender:"",
            agerender:"",
            genederrender:"",
            routeuserID:JSON.parse(localStorage.getItem('userid')),
            avgrating:[],
            nowimg:[],
            ourrankid:[],
            avgratingrank:[],
            ourranktitle:[],
            ourrankposter:[],
            ourrank:false,
          }
        },
        created(){
          this.getTopmovies()
        },
        
        mounted(){
          this.Getnowplaying()
          if (localStorage.getItem("userid")!==null && localStorage.getItem("userid")!==undefined){
            this.getuserinfo()
          }
        },
        methods:{
          
          unique (arr) {
           return Array.from(new Set(arr))
          },
          getratedmovies(){
            this.clearall();
            this.ourrank=true;
         request.get("/post/?movieId="+this.$route.params.userID).then(res=>{
           if(res.status===200) {
            for (let i=0;i<res.data.length;i++){
                this.ourrankid.push(
                     res.data[i].movieId,
                )
                this.ourranktitle.push(
                     res.data[i].movieName,
                )
                this.ourrankposter.push(
                     res.data[i].poster,
                )
            }
            this.ourrankid=this.unique(this.ourrankid)
            this.ourranktitle=this.unique(this.ourranktitle)
            for (let i=0;i<this.ourrankid.length;i++){
              request.get("/post/getAvgRatingByName?movieId="+ this.ourrankid[i]).then(res=>{
                if(res.status===200) {
                  this.avgratingrank[i]=res.data[0].avgRating

                }else{
                  this.$message({
                    type: "error",
                    message: "fail to get due to unexpected reason"
                  })
                }
              })
              
            }
           }else{
            this.$message({
              type: "error",
              message: "fail to get due to unexpected reason"
             })
            }
          })
       
      },
          Getnowplaying(){
            request2.get("/?groups=now-playing-us").then(res=>{
              if(res.status === 200){
              for(let i =0; i<10; i++) {
                this.nowimg.push({
                  value:res.data.results[i].image,
                  label:res.data.results[i].image
                })
              }
            }else {
              this.$message({
                type: "error",
                message: "unsucessfully render"
          })
        }
            })
          },
          GetAvgrating(){
            for (let i=0;i<this.movieid.length;i++){
              request.get("/post/getAvgRatingByName?movieId="+ this.movieid[i].value).then(res=>{
          if(res.status===200) {
            this.avgrating[i]=(res.data)[0].avgRating
           
          }else{
            this.$message({
              type: "error",
              message: "fail to get due to unexpected reason"
             })
          } })
            }
          },
          getuserinfo(){
              request.get("/user/info/userId="+this.routeuserID).then(res=>{
              if (res.status===200){
                  this.usernamerender=res.data.body.username
                  this.agerender=res.data.body.age,
                  this.genederrender=res.data.body.gender
              }
              })
              
              console.log(this.genederrender)
          },
          searchrelmovie(){
            this.clearall();
            this.ourrank=false
            console.log(this.movietitle);
            var outcome;
            if (this.value2!=='' && this.value3===''){
              this.getTopmovies()
            }else if(this.value2==='' && this.value3!==''){
              outcome=request2.get("/?genres="+this.value3)
            }else if(this.value2==='' && this.value3===''){
              this.getTopmovies()
            }else if(this.value2!=='' && this.value3!==''){
              outcome=request2.get("/?genres="+this.value3+'&sort='+this.value2)
            }
              outcome.then(res=>{
              if(res.status === 200){
                  // let i=0;
                  // while(i<res.data.results.length)
                  for(let i =0; i<res.data.results.length; i++) {
                    this.movietitle.push({
                      value:res.data.results[i].title,
                      label:res.data.results[i].title
                    })
                    this.movieofficialrating.push({
                      value:res.data.results[i].imDbRating,
                      label:res.data.results[i].imDbRating
                    })
                    this.movieposter.push({
                      value:res.data.results[i].image,
                      label:res.data.results[i].image
                    })
                    this.movieid.push({
                      value:res.data.results[i].id,
                      label:res.data.results[i].id
                    })
                    this.movieplot.push({
                      value:res.data.results[i].plot,
                      label:res.data.results[i].plot
                    })
                    this.moviegenre.push({
                      value:res.data.results[i].genres,
                      label:res.data.results[i].genres
                    })
                  }
                }else {
                    this.$message({
                      type: "error",
                      message: "unsucessfully search"
                })
              }
              
              this.GetAvgrating()
              console.log(this.avgrating)
            })
          
          
          },
          clearall(){
             this.movietitle=[];
             this.movieid=[];
             this.movieplot=[];
             this.moviegenre=[];
             this.movieofficialrating=[];
             this.movieposter=[];
             this.avgrating=[];
          },
  
          querySearchAsync (queryString, cb) {
            let data_list = []
            
            if (queryString.length > 0) {
              request2.get("/?title=" + queryString).then(res => {
              if(res.status === 200){
                
                for(let i =0; i<res.data.results.length; i++) {
                  this.movielist.push({
                    title: res.data.results[i].title,
                    image: res.data.results[i].image,
                    rating: res.data.results[i].imDbRating,
                    plot: res.data.results[i].plot,
                    genres: res.data.results[i].genres
  
                  });
                  data_list.push({value:res.data.results[i].title,
                                  title:res.data.results[i].title});
                  //this.postlist.push(res.data.results[i].image);
                }
                cb(data_list);
                
              }else {
                this.$message({
                  type: "error",
                  message: "unsucessfully search"
                })
              }
            })          
            } else {
              cb(data_list)
            }
          },

          handleSelect (item) {
            console.log(item)
            for(let i = 0; i<this.movielist.length; i++){
              if(item.value === this.movielist[i].title){
                this.$router.push({path: '/Moviehub/contentpageuser/' + this.movielist[i].title, query: {
                  poster: this.movielist[i].image, rating: this.movielist[i].rating, title: this.movielist[i].title, plot: this.movielist[i].plot, genres: this.movielist[i].genres}});
                
              }
            }
  
          },
          reset() {
            this.inputText = ""
          },
          jumptoContent(index) {
            this.$router.push({path: '/Moviehub/contentpageuser/' + this.movieid[index].value, query: {
              poster: this.movieposter[index].value,rating: this.movieofficialrating[index].value,title: 
              this.movietitle[index].value, plot:this.movieplot[index].value,genres:this.moviegenre[index].value
              }
            });
        
          },
          getInputValue(searchvalue) {
            console.log(searchvalue);
            
            request2.get("/?title=" + searchvalue).then(res => {
              if(res.status === 200){
                this.itemlist.length = 0;
                for(let i =0; i<res.data.results.length; i++) {
                  this.movielist.push({
                    title: res.data.results[i].title,
                    image: res.data.results[i].image,
                    rating: res.data.results[i].imDbRating
                  });
                  this.itemlist.push(res.data.results[i].title);
                  //this.postlist.push(res.data.results[i].image);
                }
                console.log(res.data.results.length);
                
              }else {
                this.$message({
                  type: "error",
                  message: "unsucessfully search"
                })
              }
            })
            
          },
          getTopmovies(){
            this.clearall()
            this.ourrank=false
            request2.get("/?groups=top_250").then(res=>{
                if(res.status === 200){
              
                    for(let i =0; i<res.data.results.length; i++) {
                      console.log("search movie");
                      this.movietitle.push({
                        value:res.data.results[i].title,
                        label:res.data.results[i].title
                      })
                      this.movieofficialrating.push({
                        value:res.data.results[i].imDbRating,
                        label:res.data.results[i].imDbRating
                      })
                      this.movieposter.push({
                        value:res.data.results[i].image,
                        label:res.data.results[i].image
                      })
                      this.movieid.push({
                        value:res.data.results[i].id,
                        label:res.data.results[i].id
                      })
                      this.movieplot.push({
                        value:res.data.results[i].plot,
                        label:res.data.results[i].plot
                      })
                      this.moviegenre.push({
                        value:res.data.results[i].genres,
                        label:res.data.results[i].genres
                      })
                    }
                    this.GetAvgrating()
                  }else {
                    this.$message({
                      type: "error",
                      message: "unsucessfully search"
                })
              }
            })
          }
        },
        
        components: { HubIcon,AvatarIcon},
      };
  </script>
  
  <style scoped>
  
  .header{
  background-color: black;
    
  }
  .recbutton{
    position:relative;
    right:180px;
  }
  .searchbar{
    margin-left: 100px;
    width:30%;
    margin-top: 17px;
  }
  .searchbut{
    padding-left:20px;
    margin-left:500px;
    margin-top: -50px;
  }
  .LRbutton{
  position:relative;
  left:580px;
  top:-35px
  }
  .seaerchthreegroup{
    margin-top: 15px;
  }
  
  .search{
  width:45%;
  margin-top: 15px;
  }
  .select{
  margin-left:10px;
  width:10%;
  right:200px;
  }
  .LRbutton{
  position:relative;
  left:560px;
  top:-35px
  }
  .setting{
  margin-top:10px
  }
  .recommend{
  position:absolute;
  font-size:x-large;
  font-weight:bold;
  color:orange
  }
  .avatar{
  position:relative;
  top:-36.5px;
  right:-600px;
  }
  .main{
    background-color: rgb(55, 55, 60);
  }
  
  .newwbutton{
    margin-top:15px;
    margin-left:400px;
    
  }
  .newbutton{
      margin-left: 700px;
      margin-top: -85px;
    }
  .box-card{
    cursor:pointer;
    
  }
  .box-card :hover{
    box-shadow:0 0 16px rgba(33,33,33,.2);
  }
  .filteroptiongroup{
    position:relative;
    left:150px;
  }
  
 
  .nowplayingtext{
    color:orange;
    left:-360px;
    position:relative;
    font-size:25px;
    margin-bottom: 15px;
  }
  .nowplayingimg{
    width:300px;
    height:330px
  }
  .nowplayingcarousel{
    width:40%;
    position:relative;
    left:5%
  }
  .ourrankcard{
    width:30%;
    position:relative;
    top:-320px;

    left:500px
  }
  .ourranktext{
    color:orange;
    font-size:25px;
    margin-bottom: 15px;
    position:relative;
    left:60px;
    top:-40px
  }
  .nowgroup{
    position:relative;
    left:360px;
  }
  </style>