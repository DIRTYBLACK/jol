<script>
import axios from 'axios'
import { Line } from "vue-chartjs"

const API_URL = 'http://localhost:8000'

export default {
    extends: Line,
        data(){
        return {
            linedata: {
                labels: [
                "1",
                "2",
                "3",
                "4",
                "5",
                "6",
                "7" 
                ],
                datasets: [
                {
                    label: "Temperature",
                    data: [2, 10, 5, 9, 0, 6, 20],
                    backgroundColor: "transparent",
                    borderColor: "rgba(170, 130, 150, 213.12)",
                    pointBackgroundColor: "rgba(130, 30, 60, 20)"
                }
                ]
            }
        }
    },
    mounted() {
        console.log(this.bardata)
    },
    created(){
      axios.get(API_URL+"/datas")
      .then((response) =>{
            var data = response.data
            data.forEach((value,index)=>{
                this.linedata.datasets[0].data[index] = value['temperature']
                console.log(value['temperature'])
            
            })
            this.renderChart(this.linedata, {
                responsive: true,maintainAspectRatio: false,
                title: {
                display: true,
                text: "My Data"
                }
          })
      })
  }
};
</script>
