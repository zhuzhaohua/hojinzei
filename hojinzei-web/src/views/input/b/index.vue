<template>
  <div style=" margin:50px ">
    <div>
        <fm-generate-form :data="jsonData" :remote="remoteFuncs" :value="editData" ref="generateForm">
        
        </fm-generate-form>
    </div>
    <div style="float:right ">
      <el-button type="primary" @click="handleSubmit" style=" ">提交</el-button>
    </div>
  </div>
</template>

<script>

export default {
  filters: {
  },
  data() {
    return {
      jsonData:{
  "list": [
    {
      "type": "grid",
      "name": "栅格布局",
      "icon": "icon-grid-",
      "columns": [
        {
          "span": 12,
          "list": [
            {
              "type": "input",
              "name": "法人名フリガナ",
              "icon": "icon-input",
              "options": {
                "width": "100%",
                "defaultValue": "",
                "required": false,
                "dataType": "string",
                "pattern": "",
                "placeholder": "",
                "customClass": "",
                "disabled": false,
                "remoteFunc": "func_1566992309151"
              },
              "novalid": {},
              "key": "1566992309151",
              "model": "input_1566992309151",
              "rules": [
                {
                  "type": "string",
                  "message": "法人名フリガナ格式不正确"
                }
              ]
            }
          ]
        },
        {
          "span": 12,
          "list": []
        }
      ],
      "options": {
        "gutter": 10,
        "justify": "start",
        "align": "top",
        "customClass": "",
        "remoteFunc": "func_1566991461966"
      },
      "key": "1566991461966",
      "model": "grid_1566991461966",
      "rules": []
    },
    {
      "type": "grid",
      "name": "栅格布局",
      "icon": "icon-grid-",
      "columns": [
        {
          "span": 12,
          "list": [
            {
              "type": "input",
              "name": "法人名ふりがな",
              "icon": "icon-input",
              "options": {
                "width": "100%",
                "defaultValue": "",
                "required": false,
                "dataType": "string",
                "pattern": "",
                "placeholder": "",
                "customClass": "",
                "disabled": false,
                "remoteFunc": "func_1566992160836"
              },
              "novalid": {},
              "key": "1566992174385",
              "model": "input1566992174385",
              "rules": [
                {
                  "type": "string",
                  "message": "法人名ふりがな格式不正确"
                }
              ]
            }
          ]
        },
        {
          "span": 12,
          "list": [
            {
              "type": "input",
              "name": "所轄",
              "icon": "icon-input",
              "options": {
                "width": "100%",
                "defaultValue": "",
                "required": false,
                "dataType": "string",
                "pattern": "",
                "placeholder": "",
                "customClass": "",
                "disabled": false,
                "remoteFunc": "func_1566992163425"
              },
              "novalid": {},
              "key": "1566992179054",
              "model": "input1566992179054",
              "rules": [
                {
                  "type": "string",
                  "message": "所轄格式不正确"
                }
              ]
            }
          ]
        }
      ],
      "options": {
        "gutter": 0,
        "justify": "start",
        "align": "top",
        "customClass": "",
        "remoteFunc": "func_1566992120181"
      },
      "key": "1566992120181",
      "model": "grid_1566992120181",
      "rules": []
    },
    {
      "type": "grid",
      "name": "栅格布局",
      "icon": "icon-grid-",
      "columns": [
        {
          "span": 12,
          "list": [
            {
              "type": "input",
              "name": "法人名",
              "icon": "icon-input",
              "options": {
                "width": "100%",
                "defaultValue": "",
                "required": false,
                "dataType": "string",
                "pattern": "",
                "placeholder": "",
                "customClass": "",
                "disabled": false,
                "remoteFunc": "func_1566992456093"
              },
              "novalid": {},
              "key": "1566992456093",
              "model": "input_1566992456093",
              "rules": [
                {
                  "type": "string",
                  "message": "法人名格式不正确"
                }
              ]
            }
          ]
        },
        {
          "span": 12,
          "list": [
            {
              "type": "input",
              "name": "業種目",
              "icon": "icon-input",
              "options": {
                "width": "100%",
                "defaultValue": "",
                "required": false,
                "dataType": "string",
                "pattern": "",
                "placeholder": "",
                "customClass": "",
                "disabled": false,
                "remoteFunc": "func_1566992471898"
              },
              "novalid": {},
              "key": "1566992471898",
              "model": "input_1566992471898",
              "rules": [
                {
                  "type": "string",
                  "message": "業種目格式不正确"
                }
              ]
            }
          ]
        }
      ],
      "options": {
        "gutter": 0,
        "justify": "start",
        "align": "top",
        "customClass": "",
        "remoteFunc": "func_1566992217236"
      },
      "key": "1566992217236",
      "model": "grid_1566992217236",
      "rules": []
    }
  ],
  "config": {
    "labelWidth": 150,
    "labelPosition": "right",
    "size": "small",
    "customClass": ""
  }
},
      editData: {},
      remoteFuncs: {
      }
    }
  },
  computed: {
  },
  created() {
  },
  methods: {
    handleSubmit () {
        this.$refs.generateForm.getData().then(data => {
            console.log(data)
        }).catch(e => {
            // 数据校验失败
        })
    }
  }
}
</script>
<style scoped>

</style>
