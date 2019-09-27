import request from '@/utils/request'

export function add(data) {
  return request({
    url: '${groupName}/${changeClassName}/create',
    method: 'post',
    data
  })
}

export function del(${pkChangeColName}) {
  return request({
    url: '${groupName}/${changeClassName}/delete/' + ${pkChangeColName},
    method: 'get'
  })
}

export function edit(data) {
  return request({
    url: '${groupName}/${changeClassName}/update',
    method: 'post',
    data
  })
}
<#--start_config-->
  #是否有效
  enable=true
  #文件生成路径
  filePath=${frontPath}\\src\\api\\${firstLowerClassName}.js
<#--end_config-->
