const Data = require('./jdata.model.js');

//새로운 데이터 생성
exports.create = (req, res) => {
  const contact = new Data({ 
    deviceId: req.body.deviceId,
    name: req.body.name,
    h: req.body.h,
    t: req.body.t,
    description: req.body.description
  });
  
  //저장 에러처리
  contact.save()
  .then(data => { res.send(data); })
  .catch(err => { 
    res.status(500).send({ message: err.message}); 
  });
};


//보기
exports.findAll = (req, res) => {
  Data.find()
  .then( contacts => { 
	res.send(contacts);
	console.log(contacts)	//test log
	  })
  .catch(err => { 
    res.status(500).send({ message: err.message }); 
  });
};

//특정한 기기 검색
exports.findOne = (req, res) => {
  Data.find({deviceId : req.params.deviceId})
  .then( contact => {
    if(!contact){
      return res.status(404).send({
        message: req.params.deviceId + "장치가 없습니다." }); 
    }
    res.send(contact);
  }).catch(err => { 
    return res.status(500).send({ message: req.params.deviceId +"에러 발생" });
  });  
};










