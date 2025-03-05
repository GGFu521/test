<template>
  <el-upload
      v-model:file-list="fileList"
      class="upload-demo"
      :multiple="false"
      :on-preview="handlePreview"
      :on-remove="handleRemove"
      :http-request="handleRequest"
      :on-success="handleSuccess"
      list-type="picture"
      ref="fileRef"
  >
    <el-button type="primary">点击上传</el-button>
  </el-upload>
</template>

<script  setup>
import { ref,watch } from 'vue'
import {getUUID, policy} from "@/api/policy";
import axios from 'axios';
const emit = defineEmits(["update:modelValue"]);

const props = defineProps({
  modelValue: {
    type: String,
    default: null
  }
});

const fileList = ref([]);
const imageInfo = ref({});
const fileRef = ref(null);

watch(() =>props.modelValue, (newValue, oldValue) => {
  fileList.value = [];
  if(newValue){
    fileList.value = [{url:newValue,name:newValue.lastIndexOf('/')===-1?newValue:newValue.substring(newValue.lastIndexOf('/')+1)}];
  }
}, { immediate: true })



const handleRemove = (uploadFile, uploadFiles) => {
  console.log(uploadFile, uploadFiles)
}

const handlePreview = (file) => {
  console.log(file)
}

const handleRequest = async (options) =>{
  const res = await policy();
  const uploadInfo = {
    policy: res.policy,
    signature: res.signature,
    ossaccessKeyId: res.accessid,
    key: res.dir + '/' + getUUID() + '_${filename}',
    dir: res.dir,
    host: res.host
  };
  const format = new FormData();
  format.append("policy",uploadInfo.policy);
  format.append("signature",uploadInfo.signature);
  format.append("key",uploadInfo.key);
  format.append("ossaccessKeyId",uploadInfo.ossaccessKeyId);
  format.append("dir",uploadInfo.dir);
  format.append("host",uploadInfo.host);
  format.append("file",options.file);
  await axios.post('http://gulimall-psw.oss-cn-hangzhou.aliyuncs.com',format);

  const urlOne = uploadInfo.host + '/' + uploadInfo.key.replace("${filename}",options.file.name);
  imageInfo.value.name = options.file.name;
  imageInfo.value.url = urlOne;
}

const handleSuccess = (response, file, fileList2) => {
  fileList.value = []
  fileList.value = [imageInfo.value];
  console.log(fileList.value)
  emit('update:modelValue', imageInfo.value.url);
}
</script>

<style scoped>
:deep(.el-upload-list){
  width: 600px;
}
</style>