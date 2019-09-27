import request from '@/utils/request'

export function generator(data, tableName) {
  return request({
    url: '/generator/gen?tableName=' + tableName,
    data,
    method: 'post'
  })
}
