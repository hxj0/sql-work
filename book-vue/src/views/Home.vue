<template>
  <el-container style="border: 1px solid #eee">

    <el-aside width="200px" style="background-color: rgb(238, 241, 246)">

      <el-menu router unique-opened>
        <el-submenu v-for="(item,index) in $router.options.routes" :index="index+''" v-if="item.show">
          <template slot="title">
            <i :class="item.class"></i>
            {{item.name}}
          </template>
          <el-menu-item v-for="(item2,index2) in item.children" :index="item2.path"
                        :class="$route.path===item2.path?'is-active':''">{{item2.name}}</el-menu-item>
        </el-submenu>
      </el-menu>
    </el-aside>

    <el-main>
      <div v-if="this.$router.currentRoute.path==='/home'" style="font-size: large">
        欢迎来到图书借阅系统！
      </div>
      <router-view></router-view>
    </el-main>
  </el-container>
</template>
<style>
</style>

<script>
  export default {

  };
</script>