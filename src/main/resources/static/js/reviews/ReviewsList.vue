<template>
  <v-container fluid v-if="profile">

    <v-layout row>
      <v-flex grow pa-1 lg10 md10 xs8>
        <v-layout align-space-around justify-start column>
          <review-row v-for="review in reviews"
                      :key="review.review_id"
                      :review="review"
                      :reviews="reviews"/>
        </v-layout>
      </v-flex>
      <v-flex shrink pa-1 class="parse-reviews-container">
        <v-card>
          <v-card-actions class="justify-center">
            <v-btn block variant="tonal" @click="parseReviews">
              Parse Reviews
            </v-btn>
          </v-card-actions>
        </v-card>
        <v-card>
          <v-card-actions class="justify-center">
            <v-btn block variant="tonal" @click="saveReviews">
              Save Reviews
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
// import MessageRow from 'components/messages/MessageRow.vue'
// import MessageForm from 'components/messages/MessageForm.vue'
import ReviewRow from "./ReviewRow.vue";

export default {
  // props: ['messages'],
  components: {
    ReviewRow
  },
  data() {
    return {
      review: null,
      // reviews: [[${reviewsData}]],
      // reviews: reviewsData,
      reviews: '',
      profile: frontendData.profile
    }
  },
  created() {
    this.getData()
  },
  // computed: {
  //   cols () {
  //     const { lg, sm } = this.$vuetify.display
  //     return lg ? [3, 9] : sm ? [9, 3] : [6, 6]
  //   },
  // },
  methods: {
    getData(){
      this.$resource('/reviews/getSavedReviews{/asin}').get({asin: this.$route.params.asin})
          .then(result =>
              result.json().then(data => {
                this.$data.reviews = data
              }))
    },
    // fetchData() {
    //   this.$resource('/reviews{/asin}').get({asin: this.$route.params.asin})
    //       .then(result =>
    //           result.json().then(data => {
    //             this.$data.reviews = data
    //           }))
    //   if (this.$data.reviews.size === 0) {
    //     alert('There is no any product reviews for this ASIN')
    //   }
    // },
    parseReviews() {
      this.$resource('/reviews{/asin}').get({asin: this.$route.params.asin})
          .then(result =>
              result.json().then(data => {
                this.$data.reviews = data
              }))
      if (this.$data.reviews.size === 0) {
        alert('There is no any product reviews for this ASIN')
      }
    },
    saveReviews() {
      // const existingProduct = this.products.find(
      //     product => product.asin === this.asin);

      if (this.reviews === 0) {
        alert('There is no any Reviews to save.')
      } else {
        this.$resource('/reviews').save({}, this.reviews)
            .then(savedReviews => savedReviews.json())
            .then((savedReviews) => {
              if (savedReviews) {
                alert('Count of saved reviews: ' + savedReviews.length + '\n '
                + 'Count of all parsed reviews: ' + this.reviews.length + '\n '
                + 'Count of reviews already existed in storage: ' + (this.reviews.length -  savedReviews.length))
                console.log('Reviews successfully saved')
              }
            })
            .catch((error) => {
              alert('Something went wrong.')
              console.log('Something went wrong.', error)
            })
      }
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
.parse-reviews-container {
  position: fixed;
  top: 50%;
  right: 10px;
  transform: translateY(-50%);
  z-index: 9999;
}
</style>
