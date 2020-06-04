<template>
<el-form ref="form" :model="form" :rules="rules" label-width="80px">
    <el-form-item label="名称" prop="name">
        <el-input style="width: 300px" v-model="form.name"></el-input>
    </el-form-item>
    <el-form-item label="作者" prop="author">
        <el-input style="width: 300px" v-model="form.author"></el-input>
    </el-form-item>
    <el-form-item label="封面" prop="cover">
        <el-upload
                class="avatar-uploader"
                action="/book/cover/upload"
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
                :before-upload="beforeAvatarUpload">
            <img v-if="imageUrl" :src="imageUrl" class="avatar" alt="封面">
            <img v-else-if="form.cover" :src="form.cover" class="avatar" alt="封面">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
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
                    price: '',
                    cover:''
                },
                categorys:[],
                bookId:'',
                imageUrl:'',
                rules: {
                    name: [
                        { required: true, message: '请输入图书名', trigger: 'blur' }
                    ],
                    author: [
                        {required: true, message: '请输入作者名', trigger: 'blur' }
                    ],
                    // cover: [
                    //     {required: true, message: '请添加封面', trigger: 'blur' }
                    // ],
                    // category:[
                    //     {required: true, message: '请选择分类', trigger: 'blur' }
                    // ],
                    count:[
                        {required: true, message: '请输入数量', trigger: 'blur' }
                    ],
                    // price:[
                    //     {required: true, message: '请输入价格', trigger: 'blur' }
                    // ]
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
                        console.log(this.form);
                        console.log(res);
                        this.form = {}
                        this.imageUrl = ''
                    });
                }
            },
            handleAvatarSuccess(res, file) {
                console.log(res);
                console.log(file);
                this.imageUrl = URL.createObjectURL(file.raw);
                this.form.cover = res.data;
                console.log(this.form.cover);
            },
            beforeAvatarUpload(file) {
                const isIMG = file.type === 'image/jpeg'
                || file.type === 'image/png';
                const isLt1M = file.size / 1024 / 1024 <= 1;

                if (!isIMG) {
                    this.$message.error('上传头像图片只能是图片JPG/PNG格式!');
                }
                if (!isLt1M) {
                    this.$message.error('上传头像图片大小不能超过 1MB!');
                }
                return isIMG && isLt1M;
            }
        }
    }
</script>

<style>
    .avatar-uploader .el-upload {
        border: 1px dashed #d9d9d9;
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
    }
    .avatar-uploader .el-upload:hover {
        border-color: #409EFF;
    }
    .avatar-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 178px;
        height: 178px;
        line-height: 178px;
        text-align: center;
    }
    .avatar {
        width: 178px;
        height: 178px;
        display: block;
    }
</style>