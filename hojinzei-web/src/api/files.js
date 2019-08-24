import request from '@/utils/request'

export function findFilesList(data) {
  return request({
    url: '/api/files/list',
    method: 'post',
    data
  })
}

export function getPathArray(fileId) {
  return request({
    url: '/api/files/path',
    method: 'get',
    params: { fileId }
  })
}

export function addFolder(data) {
  return request({
    url: '/api/files/addFolder',
    method: 'post',
    data
  })
}

export function fileDelete(data) {
  return request({
    url: '/api/files/deleteFile',
    method: 'post',
    data
  })
}

export function fileEdit(data) {
  return request({
    url: '/api/files/editFile',
    method: 'post',
    data
  })
}

export function fileCopy(data) {
  return request({
    url: '/api/files/copyFile',
    method: 'post',
    data
  })
}

export function uploadFile(data) {
  return request({
    url: '/api/files/uploadFile',
    method: 'post',
    data
  })
}
