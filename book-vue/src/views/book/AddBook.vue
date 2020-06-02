<template>
<el-form ref="form" :model="form" :rules="rules" label-width="80px">
    <el-form-item label="名称" prop="name">
        <el-input style="width: 300px" v-model="form.name"></el-input>
    </el-form-item>
    <el-form-item label="作者" prop="author">
        <el-input style="width: 300px" v-model="form.author"></el-input>
    </el-form-item>
    <el-form-item label="分类" prop="category">
        <el-select v-model="form.categoryId" placeholder="请选择图书分类">
            <el-option v-for="category in categorys" :label="category.name" :value="category.id"></el-option>
        </el-select>
    </el-form-item>
    <el-form-item label="数量" prop="count">
        <el-input style="width: 300px" v-model.number="form.count"
                  type="number"  max="200" min="0"></el-input>
    </el-form-item>
    <el-form-item label="价格" prop="price">
        <el-input style="width: 300px" v-model="form.price"
                  type="number" ></el-input>
    </el-form-item>
    <el-form-item label="简介">
        <el-input type="textarea" v-model="form.introduction"></el-input>
    </el-form-item>
    <el-form-item>
        <el-button icon="el-icon-refresh" type="primary" @click="onSubmit" v-if="bookId">立即更新</el-button>
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
                    name: '',
                    author: '',
                    categoryId: '',
                    introduction:'',
                    count: '',
                    price: ''
                },
                categorys:[],
                bookId:'',
                rules: {
                    name: [
                        { required: true, message: '请输入图书名', trigger: 'blur' }
                    ],
                    author: [
                        {required: true, message: '请输入作者名', trigger: 'blur' }
                    ],
                    category:[
                        {required: true, message: '请选择分类', trigger: 'blur' }
                    ],
                    count:[
                        {required: true, message: '请输入数量', trigger: 'blur' }
                    ],
                    price:[
                        {required: true, message: '请输入价格', trigger: 'blur' }
                    ]
                }
            }
        },
        methods: {
            init(){
                this.$axios.get("/book/category/getall").then(res => {
                    this.categorys = res.data
                });
                this.bookId = this.$route.query.bookId ||'';
                if(this.bookId){
                    this.$axios.get("/book/"+this.bookId).then(res=>{
                        this.form = res.data
                    })
                }
            },
            onSubmit() {
                if(this.bookId){
                    this.$axios.post("/book/update/",this.form).then(res => {
                        console.log(res);
                        this.form = {}
                        this.bookId = ''
                        this.$router.replace('/booklist')
                    });
                }else{
                    this.$axios.put("/book/add",this.form).then(res => {
                        console.log(res);
                        this.form = {}
                    });
                }
            }
        }
    }
</script>