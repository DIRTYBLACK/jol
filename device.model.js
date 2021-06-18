const mongoose = require('mongoose');

var jDeviceSchema = mongoose.Schema({   //스키마(Schema) 설정
  deviceId: {type: Number, require:true},
  name: {type: String, require:true},
  phone: { type: Number, require: true},
  adress: { type: String, require: true},
  description: { type: String, require: true},
  register_date: { type: Date, default:Date.now}

});

module.exports = mongoose.model('jDevice', jDeviceSchema); //소문자화 후 복수형으로 바꾸어 jdevices 컬렉션이 됨
