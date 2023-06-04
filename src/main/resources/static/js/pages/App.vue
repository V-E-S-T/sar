<template>
  <v-app>
    <v-toolbar app>
      <v-toolbar-title>Amazon Review Parser</v-toolbar-title>
      <v-btn flat
             v-if="profile"
             :disabled="$route.path === '/'"
             @click="showMessages">
        Products
      </v-btn>
      <v-spacer></v-spacer>
      <v-btn flat
             v-if="profile"
             :disabled="$route.path === '/products'"
             @click="showProducts">
        Reviews
      </v-btn>
      <v-spacer></v-spacer>
      <v-btn flat
             v-if="profile"
             :disabled="$route.path === '/profile'"
             @click="showProfile">
        {{profile.name}}
      </v-btn>
      <v-btn v-if="profile" icon href="/logout">
        <v-icon>exit_to_app</v-icon>
      </v-btn>
    </v-toolbar>
    <v-content>
<!--      <v-container v-if="!profile">-->
<!--        Необходимо авторизоваться через-->
<!--        <a href="/login">Google</a>-->
<!--      </v-container>-->
<!--      <v-container v-if="profile">-->
<!--        <messages-list :messages="messages" />-->
<!--      </v-container>-->
      <router-view></router-view>
    </v-content>
  </v-app>
</template>

<script>
import MessagesList from 'components/messages/MessageList.vue'
import ProductTable from 'products/ProductTable.vue'

export default {
  components: {
    MessagesList,
    ProductTable
  },
  data() {
    return {
      messages: frontendData.messages,
      profile: frontendData.profile,
      products: frontendData.products
    }
  },
  methods: {
    showMessages() {
      this.$router.push('/')
    },
    showProfile() {
      this.$router.push('/profile')
    },
    showProducts() {
      this.$router.push('/products')
    }
  },
  beforeMount() {
    if (!this.profile) {
      this.$router.replace('/auth')
    }
  }
}
</script>

<style>

</style>
