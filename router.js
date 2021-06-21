module.exports = (app) => {  
  const devices = require('./device.controller.js');  //contact.controller.js를 로딩
  app.get('/jdevices', devices.findAll);             //모든 기기 검색
  app.get('/jdevices/:deviceId', devices.findOne);  //특정 기기 검색
  app.post('/jdevices',devices.create);             //새로운 기기 추가
  app.put('/jdevices/:deviceId', devices.update);   //특정 기기 업데이트
  app.delete('/jdevices/:deviceId', devices.delete); //기기 데이터 삭제  

  const jdatas = require('./jdata.controller.js');  //contact.controller.js를 로딩
  app.get('/jdatas', jdatas.findAll);            //데이터 표시
  app.get('/jdatas/:deviceId', jdatas.findOne);  //보기
  app.post('/jdatas',jdatas.create);             //db저장
}
