<template>
  <v-container v-if="profile">
    <v-layout align-space-around justify-start column>
      <message-form :products="products"/>
      <message-row v-for="product in sortedProducts"
                   :key="product.product_id"
                   :product="product"
                   :products="products"
                   :update="update"/>
    </v-layout>
  </v-container>
</template>

<script>
import MessageRow from 'components/messages/MessageRow.vue'
import MessageForm from 'components/messages/MessageForm.vue'

export default {
  // props: ['messages'],
  components: {
    MessageRow,
    MessageForm
  },
  data() {
    return {
      product: null,
      products: frontendData.products,
      profile: frontendData.profile
    }
  },
  computed: {
    sortedProducts() {
      return (this.products || []).sort((a, b) => -(a.id - b.id))
    }
  },
  methods: {
    update: function (product) {
      this.product = product;
    }
  }
  // methods: {
  //   editMessage(message) {
  //     this.message = message
  //   },
  //   deleteMessage(message) {
  //     this.$resource('/message{/id}').remove({id: message.id}).then(result => {
  //       if (result.ok) {
  //         this.messages.splice(this.messages.indexOf(message), 1)
  //       }
  //     })
  //   }
  // }
}
</script>

<style>

</style>
