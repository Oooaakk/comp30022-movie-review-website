<template style="color:#F00" class="notfound">
  404 NOT FOUND!!!
</template>
<!--5秒后跳转原来页面-->
<script>

export default{
  mounted(){
    setTimeout(3);
    this.sleep()
  },
     methods:{
       sleep(){
        
        this.$router.go(-1);
       }
     }
}
</script>
<style>
  template .notfound{
    color:white;
  }
</style>