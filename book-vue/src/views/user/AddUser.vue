<template>
    <el-form ref="form" :model="form" :rules="rules"
             class="demo-ruleForm" label-width="80px">
        <el-form-item label="用户名" prop="username">
            <el-input style="width: 300px" v-model="form.username"></el-input>
        </el-form-item>
        <el-form-item label="密码">
            <el-input style="width: 300px" v-model="form.password"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
            <el-input style="width: 300px" v-model="form.email"></el-input>
        </el-form-item>
        <el-form-item>
            <el-button icon="el-icon-refresh" type="primary" @click="onSubmit" v-if="userId">立即更新</el-button>
            <el-button icon="el-icon-plus" type="primary" @click="onSubmit" v-else>立即添加</el-button>
        </el-form-item>
    </el-form>
</template>
<script>
    export default {
        created() {
            this.init()
        },
        data() {
            return {
                form: {
                    username: undefined,
                    password: undefined,
                    email:undefined
                },
                userId:'',
                rules: {
                    username: [
                        { required: true, message: '请输入用户名', trigger: 'blur' }
                    ],
                    email:[
                        {required: true, message: '请输入邮箱', trigger: 'blur' }
                    ]
                }
            }
        },
        methods: {
            init(){
                this.userId = this.$route.query.userId ||'';
                if(this.userId){
                    this.$axios.get("/book/user/"+this.userId).then(res=>{
                        this.form = res.data
                    })
                }
            },
            onSubmit() {
                if(this.userId){
                    this.$axios.post("/book/user/update/",this.form).then(res => {
                        console.log(res);
                        this.form = {}
                        this.userId = ''
                        this.$router.replace('/userlist')
                    });
                }else{
                    this.$axios.put("/book/user/add",this.form).then(res => {
                        console.log(res);
                        this.form = {}
                    });
                }
            }
        }
    }
</script>