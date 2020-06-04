import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/Login'

Vue.use(VueRouter)

  const routes = [
    {
      path: '/',
      name: 'Login',
      component: Login
    }
    // {
    //   path: '/home',
    //   name: '图书',
    //   show:true,
    //   class:'el-icon-notebook-2',
    //   component: ()=>import('../views/Home'),
    //   children:[
    //     {
    //       path:'/booklist',
    //       name:'图书列表',
    //       component:()=>import('../views/book/BookList')
    //     },
    //     {
    //       path:'/addBook',
    //       name:'添加图书',
    //       component:()=>import('../views/book/AddBook')
    //     }
    //   ]
    // },
    // {
    //   path: '/home',
    //   name: '用户',
    //   class:'el-icon-user',
    //   component: ()=>import('../views/Home'),
    //   show:true,
    //   children:[
    //     {
    //       path:'/userlist',
    //       name:'用户列表',
    //       component:()=>import('../views/user/UserList')
    //     },
    //     {
    //       path:'/addUser',
    //       name:'添加用户',
    //       component:()=>import('../views/user/AddUser')
    //     }
    //   ]
    // },
    // {
    //   path: '/home',
    //   name: '日志',
    //   class:'el-icon-document',
    //   component: ()=>import('../views/Home'),
    //   show:true,
    //   children:[
    //     {
    //       path:'/borrow',
    //       name:'借阅日志',
    //       component:()=>import('../views/borrow/Borrow')
    //     }
    //   ]
    // }
]

const router = new VueRouter({
  mode:'history',
  base: process.env.BASE_URL,
  routes
})

export default router
