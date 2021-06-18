module.exports = (app) => {  
  const devices = require('./device.controller.js');  //contact.controller.js를 로딩
  app.get('/jdevices', devices.findAll);             //모든 연락처 검색
  app.get('/jdevices/:deviceId', devices.findOne);  //특정 연락처 검색
  app.post('/jdevices',devices.create);             //새로운 연락처 추가
  app.put('/jdevices/:deviceId', devices.update);   //특정 연락처 업데이트
  app.delete('/jdevices/:deviceId', devices.delete); //특정 연락처 삭제  

  const jdatas = require('./jdata.controller.js');  //contact.controller.js를 로딩
  app.get('/jdatas', jdatas.findAll);            //데이터 표시
  app.get('/jdatas/:deviceId', jdatas.findOne);  //보기
  app.post('/jdatas',jdatas.create);             //db저장
}
