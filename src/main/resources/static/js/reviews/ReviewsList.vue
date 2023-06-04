<template>
  <v-container v-if="profile">
    <v-layout align-space-around justify-start column>
<!--      <message-form :products="products"/>-->
      <review-row v-for="review in reviews"
                   :key="review.id"
                   :review="review"
                   :reviews="reviews"/>
    </v-layout>
  </v-container>
</template>

<script>
import MessageRow from 'components/messages/MessageRow.vue'
import MessageForm from 'components/messages/MessageForm.vue'
import ReviewRow from "./ReviewRow.vue";

export default {
  // props: ['messages'],
  components: {
    ReviewRow
  },
  data() {
    return {
      review: null,
      reviews: frontendData.reviews,
      profile: frontendData.profile
    }
  }
  // computed: {
  //   sortedProducts() {
  //     return (this.products || []).sort((a, b) => -(a.id - b.id))
  //   }
  // },
  // methods: {
  //   update: function (product) {
  //     this.product = product;
  //   }
  // }
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
