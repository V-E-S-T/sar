import Vue from 'vue'
import VueRouter from "vue-router";
import MessagesList from 'components/messages/MessageList.vue'
import Auth from 'pages/Auth.vue'
import Profile from 'pages/Profile.vue'
import ProductTable from 'products/ProductTable.vue'
import ReviewsList from 'reviews/ReviewsList.vue'

Vue.use(VueRouter)

const routes = [
    { path: '/', component: MessagesList },
    { path: '/auth', component: Auth },
    { path: '/profile', component: Profile },
    { path: '/reviews', component: ReviewsList },
    // { path: '/reviews/:asin', component: ReviewsList },
    { path: '/products', component: ProductTable },
    { path: '*', component: MessagesList },

]

export default new VueRouter({
    mode:'history',
    routes
})