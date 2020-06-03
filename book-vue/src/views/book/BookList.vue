<template>
    <div>
        <el-dialog
                title="借书"
                :visible.sync="dialogVisible"
                width="30%">
            <div>
                <table>
                    <tr>
                        <td>
                            <el-tag>借阅图书</el-tag>
                        </td>
                        <td>《{{bookname}}》</td>
                    </tr>
                    <tr>
                        <td>
                            <el-tag>借阅用户名</el-tag>
                        </td>
                        <td>
                            <el-input v-model="username" placeholder="请输入借阅用户名称..."></el-input>
                        </td>
                    </tr>
                </table>
            </div>
            <span slot="footer">
                <el-button @click="canceldoAddLog">取 消</el-button>
                <el-button type="primary" @click="doAddLog">确 定</el-button>
            </span>
        </el-dialog>
        <el-form :inline="true">
            <el-form-item>
                <el-input v-model="search" placeholder="按名称或者作者搜索..."/>
            </el-form-item>

            <el-form-item>
                <el-select v-model="typeId" clearable placeholder="类别">
                    <el-option v-for="item in categorys" :value="item.id" :label="item.name"/>
                </el-select>
            </el-form-item>
            <el-button type="primary" icon="el-icon-search" @click="init()">查询</el-button>
            <el-button type="default" @click="resetData()">清空</el-button>
        </el-form>
        <el-table
                :data="tableData"
                style="width: 100%">
            <el-table-column
                    label="名称"
                    prop="name">
            </el-table-column>
            <el-table-column
                    label="作者"
                    prop="author">
            </el-table-column>
            <el-table-column
                    label="类别"
                    prop="category">
            </el-table-column>
            <el-table-column
                    label="简介"
                    prop="introduction">
            </el-table-column>
            <el-table-column
                    label="剩余/本"
                    prop="count">
            </el-table-column>

            <el-table-column
                    label="价格/元"
                    prop="price">
            </el-table-column>
            <el-table-column
                    align="right"
                    width="300px">
                <template slot="header" slot-scope="scope">
                    <span>操作</span>
                </template>
                <template slot-scope="scope">
                    <el-button
                            :disabled="scope.row.count <= 0"
                            size="mini"
                            type="success"
                            icon="el-icon-s-promotion"
                            @click="borrowBook(scope.row)">借书</el-button>
                    <el-button
                            size="mini"
                            type="primary"
                            icon="el-icon-edit"
                            @click="handleEdit(scope.row)">编辑</el-button>
                    <el-button
                            size="mini"
                            type="danger"
                            icon="el-icon-delete"
                            @click="handleDelete(scope.row)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <!-- 分页 -->
        <el-pagination
                :current-page.sync="page"
                :page-size="limit"
                :total="total"
                style="padding: 30px 0; text-align: center;"
                layout="total, prev, pager, next, jumper"
                @current-change="init"
        />
    </div>
</template>

<script>
    export default {
        created() {
            this.$axios.get("/book/category/getall").then(res => {
                this.categorys = res.data
            });
            this.init()
        },
        data() {
            return {
                page: 1,
                limit: 2,
                total:0,
                dialogVisible: false,
                tableData: [{
                    id:'',
                    name: '',
                    author:'',
                    category: '',
                    introduction:'',
                    count:'',
                    price:''
                }],
                search: '',
                bookname:'',
                username:'',
                bookId:'',
                typeId:'',
                categorys:''
            }
        },
        methods: {
            resetData(){
              this.typeId = ''
              this.search = ''
            },
            init(){
                this.$axios.get(`/book/booklist/${this.page}/${this.limit}?search=${this.search}&typeId=${this.typeId}`).then(res=>{
                    // console.log(res.data);
                    this.tableData = res.data.data;
                    this.total = res.data.total
                }).catch(e=>{
                    console.log(e);
                })
            },
            canceldoAddLog(){
                this.dialogVisible = false
                this.bookname = ''
                this.bookId = ''
                this.username = ''
            },
            borrowBook(book){
                this.dialogVisible = true
                this.bookname = book.name
                this.bookId = book.id;
            },
            doAddLog(){
                this.$axios.put('/book/borrow/addBorrowLog',{
                    bookId:this.bookId,
                    username:this.username
                }).then(res=>{
                    this.canceldoAddLog()
                    this.init()
                }).catch(e=>{
                    console.log(e);
                })
            },
            handleEdit(book) {
                this.$router.replace("/addbook?bookId="+book.id)
            },
            handleDelete(book) {
                this.$confirm('此操作将删除图书《' + book.name + '》，是否继续？', '提示', {
                    cancelButtonText: '取消',
                    confirmButtonText: '确定'
                }).then(() => {
                    this.$axios.delete("/book/delete/"+book.id).then(res=>{
                        this.init()
                    }).catch(e=>{
                        console.log(e);
                    })
                }).catch(() => {
                    this.$message.info("取消删除!");
                })
            }
        },
    }
</script>