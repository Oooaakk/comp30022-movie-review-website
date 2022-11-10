const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    allowedHosts: "all",
    proxy: {
      '/api': {
        target: 'https://moivehub-itproject-team004.herokuapp.com',

        changeOrigin: true,
        // pathRewrite: {
        //   '^api': ''
        // }
      }
    }
  }
})
