<template>
  <v-card class="my-2">
    <v-card-text primary-title>
      <i>({{ product.asin }})</i>
      {{ product.title }}
    </v-card-text>
    <v-card-actions>
      <v-btn value="Update" @click="update" small flat round>Update</v-btn>
      <v-btn icon @click="del" small>
        <v-icon>delete</v-icon>
      </v-btn>
      <v-btn value="Reviews" @click="reviews" small flat round>Reviews</v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>

function getIndex(list, id) {
  for (var i = 0; i < list.length; i++ ) {
    if (list[i].id === id) {
      return i
    }
  }

  return -1
}
    export default {
        props: ['product', 'products'],
        methods: {
          update: function () {
            this.$resource('/products{/id}').update({id: this.product.id}, {})
                .then(result =>
                    result.json().then(data => {
                      var index = getIndex(this.products, data.id)
                      this.products.splice(index, 1, data);
                    }))
          },
          del: function () {
            this.$resource('/products{/id}').remove({id: this.product.id})
                .then(result => {
                  if (result.ok) {
                    this.products.splice(this.products.indexOf(this.product), 1)
                  }
                })
          },
          reviews: function () {
            // this.$resource('/reviews').get()
            //     .then(result =>
            //         result.json().then(data => {
            //           reviewsData.push(data)
            //         }))
            // this.$router.push({ path: '/reviews'})
            this.$router.push({ path: `/reviews/${this.product.asin}`})
          }
            // edit() {
            //     this.editMessage(this.message)
            // },
            // del() {
            //     this.deleteMessage(this.message)
            // }
        }
    }
</script>

<style>

</style>
