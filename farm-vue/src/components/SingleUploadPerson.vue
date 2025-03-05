<template> 
  <div>
    <el-upload
            action="#"
            :list-type="fileType"
            :class="'avatar-uploader'"
            :multiple="false" :show-file-list="false"
            :file-list="fileList"
            :before-upload="beforeUpload"
            :on-remove="handleRemove"
            :http-request="uploadRequest"
            :on-success="handleUploadSuccess"
            :on-preview="handlePreview">
      <img v-if="fileList[0].url" :src="fileList[0].url" class="avatar">
      <el-button v-else  size="small" type="primary">点击上传</el-button>
    </el-upload>
  </div>
</template>
<script setup>


  import {ref,computed} from 'vue';
  // import {singleUploadFile} from "@/api/user";
  let props = defineProps({
    modelValue:{
      type:String
    },
    cssType:{
      type:Number,
      default:1
    },
  });
  console.log(props)
  let emit = defineEmits(["update:modelValue"]);




  const imageUrl = computed(() => props.modelValue);
  const imageName = computed(() =>{
    if (props.modelValue != null && props.modelValue !== '') {
      return props.modelValue.substr(props.modelValue.lastIndexOf("/") + 1);
    } else {
      return null;
    }
  });
  const fileList2 = computed(() =>{
    return [{
      name: imageName,
      url: imageUrl
    }]
  });

  const showFileList = computed(() =>{
    return props.modelValue !== null && props.modelValue !== ''&& props.modelValue!==undefined;
  });

  console.log(showFileList.value);
  const fileList = ref(fileList2.value)

  const dialogVisible = ref(false);
  const fileType = ref('picture');
  const isShow = ref(false)

  import {useUserStore} from "../stores";
  const userStore = useUserStore();

  //方法层
  const emitInput = (val) => {
    emit('update:modelValue', val)
  };
  const handleRemove = (file, fileList) =>{
    emitInput('');
  };
  const handlePreview = (file) =>{
    dialogVisible.value = true;
  };

  const beforeUpload =  (file) =>{
    console.log(file)
    return new Promise((resolve, reject) => {
      resolve(true)
    })
  };


  const uploadRequest =  (options) =>{
    const format = new FormData();
    format.append("file",options.file);

    console.log(options.file)
    singleUploadFile(format).then(res =>{
      fileList.value = [];
      console.log(res)
      //本地上传,把这一块放开即可
      // fileList.value.push({name: options.file.name, url: userStore.userInfo.baseUrl +res.data.filename });
      // console.log(fileList.value)
      // emitInput(res.data.filename);

      //oss上传
      fileList.value.push({name: options.file.name, url: res.data });
      console.log(fileList.value);
      console.log(options.file)
      isShow.value = true;
      if (options.file.type.startsWith("image")){
        fileType.value = 'picture'
      }else {
        fileType.value = 'text'
      }
      emitInput(res.data);
    })


  };


  const handleUploadSuccess = (res, file) => {
    console.log("上传成功...");
    // console.log(res)
    // // console.log(fileList.value)
    // // showFileList.value = true;
    // // fileList.value.pop();
    // fileList.value = []
    // // console.log(fileList.value)
    // fileList.value.push({name: file.name });
    //
    // // console.log(fileList.value)
    // console.log(fileList.value[0].url)
  }


</script>
<style scoped >

  :deep(.el-upload-list){
    width: 500px;
  }
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    width: 100%;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }

  :deep(.avatar-uploader .el-upload--text){
    width: 100%;
  }

  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 100%;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 100%;
    height: 178px;
    display: block;
  }
</style>


