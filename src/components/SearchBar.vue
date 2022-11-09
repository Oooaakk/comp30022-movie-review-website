<template>
    <div class="vue-dropdown default-theme">
      <div class="search-module clearfix">
        <input class="search-text" :placeholder="placeholder" v-model="inputText" />
        <el-icon class="searchicon" @click='search(inputText)' ><Search/></el-icon>
         
      </div>
      <div>
          <el-button class="newbutton" @click="reset()">Reset</el-button>
        </div>
      
      <ul class="list-module" v-show="isShowData" style="list-style-type: none;">
        <li style="list-style-type: none;" v-for ="(item,index) in datalist" @click="appClick(item)" :key="index">
          <!--<span><el-image :src="postlist[index]"></el-image></span>-->
          <span class="list-item-text">{{item}}</span>
        </li>
      </ul>
      <div class="tip__nodata" v-show="isShowNone">{{nodatatext}}</div>
    </div>
  </template>
   
  <script>
  import {Search} from "@element-plus/icons-vue"
    export default {
      data() {
        return {
          components:{
            Search,
          },
          datalist: [],
          //postlist:[],
          inputText: "",
          isShowData: false,
          isShowNone: false
        }
      },
      props: {
        'itemlist': Array,
        //'posterlist': Array,
        'placeholder': String,
        'nodatatext': String,
      },
      methods: {
        
        reset() {
          this.inputText = ""
        },
        appClick: function(data) {
          this.$emit('item-click', data)
          this.inputText = data
          this.isShowData = false
        },
        // computed: {
        //     matchedOptions() {
        //       if (this.inputText !== '') {
        //           return this.datalist.filter(datalist => datalist.includes(this.inputText))
        //       }
        //       return this.datalist
        //     }
        // },
        search(inputText) {
          console.log(inputText);
          let searchvalue = inputText;
          this.$emit('inputValue', searchvalue)
          this.datalist = this.itemlist
          console.log(this.datalist)
          //this.postlist = this.posterlist
          if (this.datalist.length === 0) {
            
            this.isShowNone = true
            this.isShowData = false
          } else {
            if (this.datalist.length > 30) [
              this.datalist = this.datalist.slice(0,15)
              //this.postlist = this.postlist.slice(0,10)
            ]
            this.isShowData = true
            this.isShowNone = false
          }
          if (this.inputText === "") {
            this.isShowData = false
            this.isShowNone = true
          }
        },
        inputBlur() {
          this.isShowData = false
          this.isShowNone = false
          this.inputText = ""
        }
      },
      components:{Search}
      
    }
  </script>
   
  <style lang="scss" scoped>
  
    .vue-dropdown.default-theme {
      position: absolute;
      left:35%;
      width: 30%;
      margin: 0 auto;
      margin-top: 0.25em;
      padding: 0.5em;
      z-index:10;
      // box-shadow: 0px 0px 10px #ccc;
      &._self-show {
        display: block!important;
      }
      .newbutton{
    margin-left: 440px;
    margin-top: -56px;
  }
      .search-module {
        position: relative;
        .search-text {
          width: 100%;
          height: 30px;
          padding-right: 2em;
          padding-left:0.5em;
          border-radius: 0.5em;
          box-shadow: none;
          border: 1px solid #ccc;
          // &:focus {
          //   border-color: orange;
          // }
        }
      }
   
      .list-module {
        max-height: 200px;
        overflow-y: auto;
        color:black;
        position:relative;
        top:-35px;
        width:98%;
        background-color: white;
        li {
          &._self-hide {
            display: none;
          }
          margin-top: 0.5em;
          padding: 0.5em;
          // &:hover {
          //   cursor:pointer;
          //   color: orange;
          //   background: white;
          // }
        }
      }
    }
    .tip__nodata {
      font-size: 12px;
      margin-top: 1em;
    }
    .searchicon{
      position:fixed;
      top:20px;
      left:835px;
      cursor:pointer
    }
    .list-item-text{
      cursor:pointer;
    }
    ul li:hover {background: orange;}
    ul :hover {background: orange;}
 
  </style>
  