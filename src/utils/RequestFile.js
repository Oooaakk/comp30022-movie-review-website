import axios from 'axios'
import router from "@/router"

const request = axios.create({
  baseURL: "https://moivehub-itproject-team004.herokuapp.com",
  timeout: 100000
})

//const whiteUrls = ["/user/login", '/user/register']
const whiteUrls = ["/post"]
request.interceptors.request.use(config => {
  config.headers['Content-Type'] = 'application/json;charset=utf-8'

  let userJson = localStorage.getItem("user")
  if (whiteUrls.includes(config.url)) {
    if (!userJson) {
      router.push("/moviehub/loginpage")
    } else {
      let user = JSON.parse(userJson)
      config.headers['Authorization'] = 'Bearer ' + user
    }
  }
  return config
}, error => {
  return Promise.reject(error)
})

// response 拦截器
// 可以在接口响应后统一处理结果
request.interceptors.response.use(
  response => {
    //let res = response.data
    // // file
    // if (response.config.responseType === 'blob') {
    //   return res
    // }
    // 兼容服务端返回的字符串数据
    // if (typeof res === 'string') {
    //   res = res ? JSON.parse(res) : res
    // }
    // 验证token
    if (response.status === 401) {
      this.$message({
        message: "token expired,please relogin!",
        type: "error",
      })
      router.push("/moviehub/loginpage")
    }
    return response
  },
  error => {
    console.log('err' + error) // for debug
    return Promise.reject(error)
  }
)


export default request