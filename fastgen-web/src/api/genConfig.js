import request from '@/utils/request'

/**
 * 获取总项目配置
 */
export function getBaseConfig() {
  return request({
    url: 'config/getBaseConfig',
    method: 'get'
  })
}

/**
 * 更新总项目配置
 */
export function updateBaseConfig(data) {
  return request({
    url: 'config/updateBaseConfig',
    data,
    method: 'post'
  })
}

/**
 * 获取ftl模板名
 */
export function getFtlNames() {
  return request({
    url: 'config/getFtlNames',
    method: 'get'
  })
}

/**
 * 获取用户配置
 */
export function getCustomConfig() {
  return request({
    url: 'config/getCustomConfig',
    method: 'get'
  })
}

/**
 * 更新用户配置
 */
export function updateCustomConfig(data) {
  return request({
    url: 'config/updateCustomConfig',
    data,
    method: 'post'
  })
}

