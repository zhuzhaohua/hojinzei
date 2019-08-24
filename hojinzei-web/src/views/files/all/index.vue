<template>
  <div class="app-container">
    <div class="files-path">
      <div class="files-path-item">
        当前位置：
      </div>
      <div class="files-path-item">
        <el-button type="text" @click="goThis('1')">{{ name }}</el-button>
      </div>
      <div v-for="pathTmp in filePathArray" :key=" pathTmp.pathId " class="files-path-item">
        / <el-button type="text" @click="goThis(pathTmp.pathId)">{{ pathTmp.pathName }}</el-button>
      </div>
    </div>
    <div class="files-title">
      <el-row>
        <el-button-group v-if=" copyActive ">
          <el-button type="primary" plain round icon="el-icon-document-delete" @click="copyCancel">取消</el-button>
          <el-button type="primary" plain round icon="el-icon-document-checked" @click="pasteFile">粘贴</el-button>
        </el-button-group>
        <el-button-group v-else-if=" selectedCount > 0 ">
          <el-button type="primary" plain round icon="el-icon-share">共享</el-button>
          <el-button type="primary" plain round icon="el-icon-download">下载</el-button>
          <el-button type="primary" plain round icon="el-icon-delete" @click="deleteDialog">删除</el-button>
          <el-button type="primary" plain round icon="el-icon-document-copy">复制</el-button>
          <el-button type="primary" plain round icon="el-icon-scissors">剪切</el-button>
        </el-button-group>
        <el-button-group v-else>
          <el-button type="success" plain round icon="el-icon-upload" @click="uploadActive = true">文件上传</el-button>
          <el-button type="success" plain round icon="el-icon-circle-plus" @click="newFolderDialogVisible = true">新建文件夹</el-button>
        </el-button-group>
      </el-row>
    </div>
    <div class="files-content">
      <div>
        <el-table
          ref="findedTable"
          v-loading="listLoading"
          :data="findedList"
          :row-key="getRowKey"
          tooltip-effect="dark"
          empty-text="无文件"
          stripe
          style="width: 100%"
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" width="55" :reserve-selection=" true " align="center" />
          <el-table-column label="文件名/文件夹" width="500px">
            <template slot-scope="scope">
              <div v-if="scope.row.fileType === 0">
                <svg-icon icon-class="folder" />
                <el-button type="text" @click="goThis(scope.row.fileId)">{{ scope.row.fileName }}</el-button>
              </div>
              <div v-if="scope.row.fileType === 1">
                {{ scope.row.fileName }}
              </div>

            </template>
          </el-table-column>
          <el-table-column label="文件大小" width="150">
            <template slot-scope="scope">
              {{ scope.row.fileType === 0? '-':scope.row.fileSize }}
            </template>
          </el-table-column>
          <el-table-column prop="createDate" label="创建时间" width="200" />
          <el-table-column prop="finishDate" label="修改日期" width="200" />
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-popover
                placement="right"
                trigger="click"
              >
                <el-button-group size="mini">
                  <el-button type="primary" plain icon="el-icon-edit" @click="edit(scope.row)" />
                  <el-button type="primary" plain icon="el-icon-share" />
                  <el-button type="primary" plain icon="el-icon-download" />
                  <el-button type="primary" plain icon="el-icon-delete" @click="deleteDialog(scope.row.fileId)" />
                  <el-button type="primary" plain icon="el-icon-document-copy" @click="copyFile(scope.row, 1)" />
                  <el-button type="primary" plain icon="el-icon-scissors" @click="copyFile(scope.row, 2)" />
                </el-button-group>
                <el-button slot="reference" type="text" icon="el-icon-more" />
              </el-popover>
            </template>
          </el-table-column>
        </el-table>
        <div style="margin-top: 20px">
          <el-pagination
            :current-page=" queryForm.page "
            :page-size=" queryForm.limit "
            :total=" listTotal "
            layout="prev, pager, next, jumper"
            @current-change="handleCurrentChange"
          />
        </div>
      </div>
    </div>

    <el-dialog title="新建文件夹" :visible.sync="newFolderDialogVisible" width=" 40% ">
      <el-form :model="newFolderForm" :inline="true">
        <el-form-item label="名称">
          <el-input v-model="newFolderForm.folderName" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="addFolder">确 定</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <el-dialog title="修改文件名" :visible.sync="editDialogVisible" width=" 40% ">
      <el-form :model="editForm" :inline="true">
        <el-form-item label="名称">
          <el-input v-model="editForm.fileName" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="editFile">确 定</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <el-dialog title="文件上传" :visible.sync="uploadActive" width=" 40% " center>
      <el-upload
        ref="upload"
        class="upload-demo"
        action="/upload"
        multiple
        :file-list="uploadFileList"
        :auto-upload="false"
      >
        <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
        <el-button style="margin-left: 10px;" size="small" type="success" @click="tusUpload">上传到服务器</el-button>
      </el-upload>
    </el-dialog>

    <el-dialog
      title="删除确认"
      :visible.sync="deleteDialogVisible"
      width="30%"
      center
    >
      <span>所选的文件或文件夹及其子文件夹都将删除？请确认！</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="deleteDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="deleteFiles">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>

import { findFilesList, getPathArray, addFolder, fileDelete, fileEdit, fileCopy, uploadFile } from '@/api/files'
import { mapGetters } from 'vuex'
import tus from 'tus-js-client'
import { TUS_SERVER_URL } from '@/settings'

export default {
  filters: {
  },
  data() {
    return {
      queryForm: {
        limit: 7,
        page: 1,
        fileId: '1'
      },
      findedList: [],
      selectedList: [],
      selectedCount: 0,
      listTotal: 0,
      listLoading: true,
      uploadActive: false,
      uploadFileList: [],
      newFolderDialogVisible: false,
      newFolderForm: {
        parentId: '',
        folderName: ''
      },
      editDialogVisible: false,
      editForm: {
        fileId: '',
        fileName: ''
      },
      deleteDialogVisible: false,
      deleteFileList: [],
      copyActive: false,
      copyForm: {
        copyType: 1,
        copyIds: [],
        copyNames: [],
        oldParentId: '',
        newParentId: ''
      },
      filePathArray: [],
      getRowKey: (row) => {
        return row.fileId
      }
    }
  },
  computed: {
    ...mapGetters([
      'name'
    ])
  },
  created() {
    this.fetchData()
  },
  methods: {
    fetchData() {
      this.listLoading = true
      findFilesList(this.queryForm).then(response => {
        this.findedList = response.data.items
        this.listTotal = response.data.total

        const _thisPageSelectedList = this.selectedList[this.queryForm.page]
        if (_thisPageSelectedList && _thisPageSelectedList.length > 0) {
          _thisPageSelectedList.forEach(row => {
            this.$refs.findedTable.toggleRowSelection(row, true)
          })
        }
        this.listLoading = false
      })
      this.getPaths(this.queryForm.fileId)
    },
    handleSelectionChange(val) {
      this.selectedList[this.queryForm.page] = val
      this.selectedCount = 0
      this.selectedList.forEach(arr => {
        this.selectedCount += arr.length
      })
    },
    handleCurrentChange(val) {
      this.queryForm.page = val
      this.fetchData()
    },
    edit(file) {
      this.editForm.fileId = file.fileId
      this.editForm.fileName = file.fileName

      this.editDialogVisible = true
    },
    editFile() {
      this.listLoading = true
      fileEdit(this.editForm).then(response => {
        if (response.data === 0) {
          this.$message.error('文件修改失败')
          this.listLoading = false
          return
        }
        this.$message({
          message: '文件修改完成',
          type: 'success'
        })
        this.fetchData()
        this.editDialogVisible = false
        this.listLoading = false
      })
    },
    getPaths(id) {
      this.listLoading = true
      getPathArray(id).then(response => {
        this.filePathArray = response.data
        this.listLoading = false

        this.clearSelected()
      })
    },
    goThis(id) {
      this.queryForm.fileId = id
      this.queryForm.page = 1
      this.fetchData()
    },
    addFolder() {
      this.listLoading = true
      this.newFolderForm.parentId = this.queryForm.fileId
      addFolder(this.newFolderForm).then(response => {
        if (response.data === 0) {
          this.$message.error('文件夹已经存在！')
          this.listLoading = false
          return
        }
        this.$message({
          message: '文件夹创建成功',
          type: 'success'
        })
        this.fetchData()
        this.newFolderDialogVisible = false
        this.listLoading = false
      })
    },
    deleteDialog(id) {
      if (typeof (id) === String) {
        this.deleteFileList.push(id)
      } else if (this.selectedList && this.selectedList.length > 0) {
        this.selectedList.forEach(pageSelectedList => {
          if (pageSelectedList && pageSelectedList.length > 0) {
            pageSelectedList.forEach(file => {
              this.deleteFileList.push(file.fileId)
            })
          }
        })
      }

      if (this.deleteFileList.length > 0) {
        console.log(this.deleteFileList)
        this.deleteDialogVisible = true
      }
    },
    deleteFiles() {
      fileDelete({ files: this.deleteFileList }).then(response => {
        if (response.data === 0) {
          this.$message.error('删除失败')
          this.listLoading = false
          return
        }
        this.$message({
          message: '删除成功！',
          type: 'success'
        })
        this.fetchData()
        this.clearSelected()
        this.deleteDialogVisible = false
        this.listLoading = false
      })
    },
    copyFile(file, type) {
      this.copyForm.copyType = type
      this.copyForm.copyIds.push(file.fileId)
      this.copyForm.copyNames.push(file.fileName)
      this.copyForm.oldParentId = file.parentId
      this.copyActive = true
    },
    pasteFile() {
      this.copyForm.newParentId = this.queryForm.fileId

      fileCopy(this.copyForm).then(response => {
        this.copyCancel()
        this.fetchData()
        this.clearSelected()
      })
    },
    copyCancel() {
      this.copyForm.copyIds = []
      this.copyForm.copyNames = []
      this.copyForm.oldParentId = ''
      this.copyForm.newParentId = ''
      this.copyActive = false
    },
    clearSelected() {
      if (!this.copyActive) {
        this.selectedList = []
        this.selectedCount = 0
      }
    },
    tusUpload() {
      var file = this.$refs.upload.uploadFiles[0].raw
      var data = {
        parentId: this.queryForm.fileId,
        fileSize: file.size,
        fileName: file.name
      }

      uploadFile(data).then(response => {
        this.fileId = response.data

        // Create a new tus upload
        var upload = new tus.Upload(file, {
          endpoint: TUS_SERVER_URL,
          retryDelays: [0, 3000, 5000, 10000, 20000],
          metadata: {
            filename: file.name,
            fileSize: file.size,
            fileId: this.fileId
          },
          onError: function(error) {
            console.log('Failed because: ' + error)
          },
          onProgress: function(bytesUploaded, bytesTotal) {
            var percentage = (bytesUploaded / bytesTotal * 100).toFixed(2)
            console.log(bytesUploaded, bytesTotal, percentage + '%')
          },
          onSuccess: function() {
            console.log('Download %s from %s', upload.file.name, upload.url)
          }
        })

        upload.start()
      })
      this.uploadFileList = []
    }
  }
}
</script>
<style scoped>
.files-path {
  margin: 10px;
}
.files-path-item {
  display: inline-block;
}
</style>
