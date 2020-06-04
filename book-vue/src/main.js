import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import axios from 'axios'

axios.defaults.baseURL=''

Vue.prototype.$axios=axios

Vue.use(ElementUI)

Vue.config.productionTip = false

// router.beforeEach((to, from, next)=>{
//   if(window.sessionStorage.getItem("user"))next()
//   else next('/?redirect='+to.path)
// })

Vue.prototype.$alert = ElementUI.MessageBox.alert
Vue.prototype.$confirm = ElementUI.MessageBox.confirm

axios.interceptors.response.use(success => {
  if (success.status && success.status === 200 && success.data.code === 20000
      && success.data.msg ) {
    ElementUI.Message.success({message: success.data.msg})
  }
  if (success.data.code===40000 && success.data.msg) {
    ElementUI.Message.error({message: success.data.msg})
  }
  return success.data;
}, error => {
    if (error.response.data.msg) {
      ElementUI.Message.error({message: error.response.data.msg})
    } else {
      ElementUI.Message.error({message: '未知错误!'})
    }
})

router.beforeEach((to, from, next) => {
    if (to.path === '/') {
        next();
    } else {
        if (window.sessionStorage.getItem("user")) {
            next();
        } else {
            next('/?redirect=' + to.path);
        }
    }
})

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
