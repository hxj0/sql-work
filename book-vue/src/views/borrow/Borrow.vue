<template>
    <div>
        <el-form style="margin-left: -1100px">
            <el-button
                    icon="el-icon-plus"
                    type="success"
                    v-if="$store.state.role !== 'user'"
                    @click="exportTable">导出数据</el-button>
        </el-form>
        <el-table id="table-borrow-data"
                  :header-cell-style="thStyleFun"
                  :cell-style="cellStyleFun"
                  :default-sort = "{prop: 'borrowDate', order: ''}"
                  :data="tableData.filter(data => !search ||
                data.username.toLowerCase().includes(search.toLowerCase()) ||
                data.bookname.toLowerCase().includes(search.toLowerCase()) ||
                data.borrowDate.toLowerCase().includes(search.toLowerCase()) ||
                data.returnDate.toLowerCase().includes(search.toLowerCase()))"
                style="width: 80%">
            <el-table-column
                    label="用户名"
                    prop="username">
            </el-table-column>
            <el-table-column
                    label="图书名"
                    prop="bookname">
            </el-table-column>
            <el-table-column
                    label="借阅日期"
                    sortable
                    prop="borrowDate">
            </el-table-column>
            <el-table-column
                    label="归还日期"
                    sortable
                    prop="returnDate">
            </el-table-column>
            <el-table-column
                    align="right"
                    width="200px">
                <template slot="header" slot-scope="scope">
                    <el-input
                            v-model="search"
                            size="mini"
                            placeholder="输入关键字搜索"/>
                </template>
                <template slot-scope="scope">
                    <el-button
                            :disabled="scope.row.returnDate!=null"
                            size="mini"
                            type="primary"
                            icon="el-icon-receiving"
                            @click="handleRecive(scope.row)">归还</el-button>
                    <el-button
                            size="mini"
                            type="danger"
                            icon="el-icon-delete"
                            v-show="$store.state.role !== 'user'"
                            @click="handleDelete(scope.row)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
    </div>
</template>

<script>
    import fileSaver from 'file-saver'
    import xlsx from 'xlsx'
    export default {
        created() {
            this.init()
        },
        data() {
            return {
                tableData: [{
                    bookId:'',
                    userId:'',
                    username: '',
                    bookname:'',
                    borrowDate:'',
                    returnDate:''
                }],
                search: ''
            }
        },
        methods: {
            thStyleFun() {
                return 'text-align:center'
            },
            cellStyleFun() {
                return 'text-align:center'
            },
            init(){
                this.$axios.get('/book/borrow/all?username='+this.$store.state.username).then(res=>{
                    // console.log(res.data);
                    this.tableData = res.data;
                    console.log(this.tableData);
                }).catch(e=>{
                    console.log(e);
                })
            },
            handleRecive(log){
                this.$axios.post('/book/borrow/recive',{
                    bookId:log.bookId,
                    userId:log.userId,
                    borrowDate:log.borrowDate
                }).then(res=>{
                    this.init()
                })
            },
            handleDelete(log) {
                this.$confirm('此操作将删除当前借阅日志，是否继续？', '提示', {
                    cancelButtonText: '取消',
                    confirmButtonText: '确定'
                }).then(() => {
                    this.$axios.delete(`/book/borrow/delete/${log.bookId}/${log.userId}?borrowDate=${log.borrowDate}`).then(res=>{
                        this.init()
                    }).catch(e=>{
                        console.log(e);
                    })
                }).catch(() => {
                    this.$message.info("取消删除!");
                })
            },
            exportTable(){
                let box = xlsx.utils.table_to_book(document.querySelector('#table-borrow-data'))
                let out = xlsx.write(box, {
                    bookType: 'xlsx',
                    bookSST: true,
                    type: 'array'
                })
                try {
                    fileSaver.saveAs(
                        new Blob([out], {
                            type: 'application/octet-stream'
                        }),
                        'sheet.xlsx'
                    )
                } catch (e) {
                    // 错误处理方式
                }
                return out
            }
        },
    }
</script>
