import request from '@/utils/request'

export function get() {
  return request({
    url: 'config/getConfig',
    method: 'get'
  })
}

export function getFtlNames() {
  return request({
    url: 'config/getFtlNames',
    method: 'get'
  })
}

export function update(data) {
  return request({
    url: 'config/updateConfig',
    data,
    method: 'post'
  })
}
