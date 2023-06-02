// import {createApp} from 'vue'
import Vue from 'vue'
import VueResource from 'vue-resource'
import App from 'pages/App.vue'
import Vuetify from 'vuetify'
import 'vuetify/dist/vuetify.min.css'
import router from 'router/router'

Vue.use(VueResource)
Vue.use(Vuetify)
new Vue({
    el: '#app',
    router,
    render: a => a(App)
})



// let app;
// app = Vue.createApp({
//     el: '#app',
//     render: a => a(App),
//     // vuetify: new Vuetify({})
// });
// app.use(VueResource)
// app.use(Vuetify)
