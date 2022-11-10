import { createRouter, createWebHashHistory } from 'vue-router'
const routes = [
  { path: '/', redirect: '/Moviehub/mainpageuser' },
  {
    path: '/Moviehub/loginpage',
    name: 'login',
    meta: {
      title: '登录界面',
      needLogin: true,
    },
    component: () => import('@/views/LoginPage.vue'),
  },

  {
    path: '/Moviehub/mainpageuser',
    name: 'MoviemainPage2',
    meta: {
      index: true,
      title: '电影主页面2',
    },
    component: () => import('@/views/MainPageUser.vue'),
  },
  {
    path: '/Moviehub/dashboard/:userID',
    name: 'MovieDashbord',
    meta: {
      index: true,
      title: '用户主界面',
    },
    component: () => import('@/views/DashBoard.vue'),
  },

  {
    path: '/Moviehub/registerpage',
    name: 'Register',
    meta: {
      title: '注册界面',
      needLogin: true,
    },
    component: () => import('@/views/RegisterPage.vue'),
  },
  {
    path: '/Moviehub/forgetpassword',
    name: 'forgetpassword',
    meta: {
      title: '忘记密码',
      needLogin: true,
    },
    component: () => import('@/views/ForgetPassword.vue'),
  },

  {
    path: '/Moviehub/contentpageuser/:userID',
    name: 'ContentPageUser',
    meta: {
      index: true,
      title: '登录内容',
    },
    component: () => import('@/views/ContentUser.vue'),
  },

  {
    path: '/:path(.*)*',
    name: '找不到页面',
    meta: {
      title: '忘记密码',
      needLogin: true,
    },
    component: () => import('@/views/PageNotFound.vue'),
  },
]
const router = createRouter({
  history: createWebHashHistory(),
  routes,
})
router.beforeEach((to, from, next) => {
  if (
    to.name === 'login' ||
    to.name === 'Register' ||
    to.name === 'forgetpassword' ||
    to.name === 'MoviemainPage2' ||
    to.name === 'ContentPageUser'
  ) {
    return next()
  } else {
    const tokenStr = window.localStorage.getItem('user')
    if (!tokenStr) {
      alert('must login first!!')
      return next('/moviehub/loginpage')
    }
    next()
  }

  //获取token
})

export default router
