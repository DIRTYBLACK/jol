<template>
<v-app>
    <v-container id="dashboard">
        <v-row>
            <v-col>
                <device-table/>
            </v-col>
        </v-row>
        <v-row>
            <v-col>
                <data-table/>
            </v-col>
            <v-col>
                <data-chart/>
            </v-col>
        </v-row>
    </v-container>
</v-app>
</template>

<script>
import DeviceTable from './components/DeviceTable';
import DataTable from './components/DataTable';
import DataChart from './components/DataChart';

export default {
  name: 'App',
  components: {
    DeviceTable,
    DataTable,
    DataChart
  },
  data: () => ({
    //
  }),
}
</script>
<style scoped>
  h2{
    margin-top:20px;
    margin-left: 10px;
    color:blue;
  }
</style>
