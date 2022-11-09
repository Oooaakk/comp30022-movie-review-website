<template>

  <div class="common-layout">
    <el-container>
      <el-header class="header">
        <div class="seaerchthreegroup">
         
  
            <el-autocomplete 
              placeholder="search your movie" 
              popper-class="my-autocomplete" 
              class="input-with-select" 
              v-model="keywords" 
              :fetch-suggestions="querySearchAsync" 
              :trigger-on-focus="false" 
              @select="handleSelect">
            </el-autocomplete>
  
        <div>
          <el-button type="primary" class="searchbut"  >Search</el-button>
        </div>
        <div>
          <el-button class="newbutton" @click="reset()">Reset</el-button>
        </div> 
      </div>
  
      <div v-if="routeuserID===''|| routeuserID===null">
       <el-button type="warning" round class="LRbutton" @click="$router.push('/moviehub/loginpage')">Login/Register</el-button> 
      </div>
      <div v-else>
        <AvatarIcon :routeID="routeuserID"/>
      </div>
  
      </el-header>
      
      <el-main class="main">
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
  
  <el-button @click="searchrelmovie()">recommended</el-button></div>
        <el-divider />

        
        <!-- <el-space wrap>
          
            <el-card v-for="(item,i) in movietitle" :key="i" class="box-card" 
             style="width: 250px;margin-right:20px"
             @click="jumptoContent(i)">

                <div class="card-header">
                  <span>{{movietitle[i].value}}({{moviegenre[i].value}})</span>
                  <el-divider/>
  
                  <span><el-image :src="movieposter[i].value"></el-image></span>
                  <el-divider/>
                  <span>imdb rating:{{movieofficialrating[i].value}}</span>
                </div>
            </el-card>
          
        </el-space> -->
        

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
            routeuserID:JSON.parse(localStorage.getItem('userid'))
          }
        },
        mounted(){
          this.getTopmovies()
        },
        methods:{
          searchrelmovie(){
            this.clearall();
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
            })
          
          
          },
          clearall(){
             this.movietitle=[];
             this.movieid=[];
             this.movieplot=[];
             this.moviegenre=[];
             this.movieofficialrating=[];
             this.movieposter=[];
          },
  
  // 查询
          querySearchAsync (queryString, cb) {
            // data 为可选列表数据
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
            
            /*if(this.last !== searchvalue){
              this.itemlist.length = 0;
            }*/
            
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
            // 请求获取筛选列表
            
          },
          getTopmovies(){
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
  .searchbar{
    margin-left: 100px;
    width:20%;
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
  top:-20px
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
    left:70px;
  }
  .seaerchthreegroup{
  
    position:relative;
    top:13px;
    left:0px;
  
  }
  </style>