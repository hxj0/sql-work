<template>
    <div>
        <el-table
                :data="tableData"
                height="600"
                border
                :header-cell-style="thStyleFun"
                :cell-style="cellStyleFun"
                style="width: 100%">
            <el-table-column
                    prop="username"
                    label="用户名"
                    width="180">
            </el-table-column>
            <el-table-column
                    prop="email"
                    label="邮箱"
                    width="180">
            </el-table-column>
            <el-table-column
                    prop="role"
                    label="角色"
                    width="180">
            </el-table-column>
            <el-table-column label="是否禁用"
                    width="100px"
                    prop="isEnabled">
                <template slot-scope="scope">
                    <el-button round
                            size="mini" :type="scope.row.isEnabled?'primary':'danger'"
                               :icon="scope.row.isEnabled?'el-icon-circle-check':'el-icon-circle-close'"
                            @click="handleDisabledOrEnabled(scope.row)">{{scope.row.isEnabled?'启用':'禁用'}}</el-button>
                </template>
            </el-table-column>
            <el-table-column label="编辑用户"
                             width="100px">
                <template slot-scope="scope">
                    <el-button
                               size="mini" type="text" icon="el-icon-edit"
                               @click="handleEdit(scope.row)">编辑</el-button>
                </template>
            </el-table-column>
        </el-table>
    </div>
</template>

<script>
    export default {
        created() {
            this.init()
        },
        data() {
            return {
                tableData: [{
                    id: '',
                    username: '',
                    email: '',
                    role: '',
                    isEnabled: ''
                }]
            }
        },
        methods:{
            init(){
                this.$axios.get('/book/user/all').then(res=>{
                    this.tableData = res.data
                    console.log(res.data);
                })
            },
            thStyleFun() {
                return 'text-align:center'
            },
            cellStyleFun() {
                return 'text-align:center'
            },
            handleDisabledOrEnabled(user){
                this.$axios.post('/book/user/enabledorDisabled/'+user.id+
                    '?isEnabled='+user.isEnabled).then(res=>{
                        if (res.code === 20000) user.isEnabled = !user.isEnabled
                })
            },
            handleEdit(user){
                this.$router.replace('/adduser?userId='+user.id)
            }
        }
    }
</script>