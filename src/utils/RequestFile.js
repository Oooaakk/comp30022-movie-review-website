import axios from 'axios'
import router from "@/router"
const request = axios.create({
  baseURL: "https://moivehub-itproject-team004.herokuapp.com",
  timeout: 100000
})

const whiteUrls = ["/user/login", "/user/register", "/user/forgotPassword", "/user/forgotPassword/email",
  "/user/register/email"]
//const whiteUrls = ["/post", "/photo", "/post/:id*"]
request.interceptors.request.use(config => {
  config.headers['Content-Type'] = 'application/json;charset=utf-8'

  let userJson = localStorage.getItem("user")
  let r = JSON.parse(localStorage.getItem("refreshuser"))


  if (!whiteUrls.includes(config.url)) {
    if (!userJson) {
      // router.push("/moviehub/loginpage")
    } else {

      config.headers['Authorization'] = 'Bearer ' + JSON.parse(userJson)
    }
  }
  return config
}, error => {
  return Promise.reject(error)
})

request.interceptors.response.use(
  response => {
    if (response.status === 401) {
      console.log(response.status)
      alert("token expired,please relogin!")
      router.push("/moviehub/loginpage")
    }
    return response
  },
  async function (error) {
    const originalRequest = error.config
    console.log('err' + error) // for debug
    console.log(error.response.status)
    if (error.response.status === 0) {

      alert("token expired,please relogin!")
      router.push("/moviehub/loginpage")
    }
    return Promise.reject(error)
  }
)

export default request