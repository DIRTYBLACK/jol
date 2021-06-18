import Vue from 'vue'
import App from './App.vue'
import vuetify from './plugins/vuetify'
import axios from 'axios'
import VueAxios from 'vue-axios'

Vue.use(VueAxios, axios)

Vue.config.productionTip = false
Vue.prototype.$bus = new Vue()  // 이벤트 버스용 빈 인스턴스

new Vue({
  vuetify,
  render: h => h(App)
}).$mount('#app')
