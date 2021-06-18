<template>
  <div id="app">
	<v-data-table :headers="headers" :items="items" class="elevation-1">
		<template v-slot:item="row">
		<tr>
			<td>{{row.item.deviceId}}</td>
			<td>{{row.item.t}}</td>
			<td>{{row.item.h}}</td>
			<td>{{row.item.register_date}}</td>
		</tr>
      </template>
	</v-data-table>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  data () {
    return {
      urlinfo:'http://localhost:8000/jdatas/',
      dataInfo:{
        _id: null,
        deviceId: null,
        t:null,
        h:null,
        register_date: null
      },
      dialog: false,
      dialogTitle:null,

      headers: [
        { text: '번호', align: 'left', value: 'deviceId'},
        { text: '온도', align: 'left', value: 't'},
        { text: '습도', align: 'left',value: 'h'},
		{ text: '등록일', align: 'left', value: 'register_date'}
      ],
      items:[]
    }
  },
  created(){
    this.$bus.$on('deviceSelected',this.refleshData)
/*
    axios.get(this.urlinfo+1) //서버에 요청하기
    .then((res) => {
      console.log(res.data); //성공시
      this.items = res.data;
    })
    .catch((err) => {
      alert('에러 발생: ' + err); //에러 발생
    });
    */
  },
  methods: {
      refleshData(deviceId) {
        axios.get(this.urlinfo+deviceId) //서버에 요청하기
        .then((res) => {
        console.log(res.data); //성공시
        this.items = res.data;
        })
        .catch((err) => {
        alert('에러 발생: ' + err); //에러 발생
        });          
      }
  }
}
</script>
<style scoped>
  div{
    margin:0 5px 0 5px;
  }
</style>