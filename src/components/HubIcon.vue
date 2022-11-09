<template>
 <div class="hub" @click="$router.push('/Moviehub/mainpageuser')">
<span contenteditable="false">Movie</span>
<span contenteditable="false">Hub</span>
</div>
</template>
<script>
  export default{
    data(){
      return{
       
      }
    }
  }
</script>
<style >
 .hub {
  display: inline;
  font-family: sans-serif;
  font-weight: bold;
  font-size: 3vw;
  position: absolute;
  top:20px;
  left: 5%;
  transform: translate(-50%, -50%);

}
.hub:hover{
  box-shadow: 0 16px 32px 0 orange;
  cursor:pointer;
    transition-delay: 0s !important;
}
    
.hub span:nth-child(1) {
color: white;
font-size:large;

}
.hub span:nth-child(2) {
background: #FF9900;
color: black;
border-radius: 1vw;
padding: 0.05vw 0.25vw 0.25vw 0.2vw;
display: inline-block;
font-size:large;
margin-left:1.5px;
}
body {
margin: 0;
min-height: 100%;
height: 100%;
position: relative;
}
</style>
