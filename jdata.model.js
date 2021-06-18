const mongoose = require('mongoose');

var jDataSchema = mongoose.Schema({   //스키마(Schema) 설정
  deviceId: {type: Number, require:true}, //번호
  t: { type: Number, require: true}, //온도
  h: { type: Number, require: true}, //습도
  register_date: { type: Date, default:Date.now} //시간
});

module.exports = mongoose.model('jData', jDataSchema); //소문자화 후 복수형으로 바꾸어 jdatas 컬렉션이 됨