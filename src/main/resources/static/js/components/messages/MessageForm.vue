<template>
  <v-layout row>
    <v-text-field
        label="New rpoduct"
        placeholder="Write ASIN"
        v-model="asin"
    />
    <v-btn @click="save">
      Save
    </v-btn>
  </v-layout>
</template>

<script>
    // function getIndex(list, id) {
    //     for (var i = 0; i < list.length; i++ ) {
    //         if (list[i].id === id) {
    //             return i
    //         }
    //     }
    //
    //     return -1
    // }

    export default {
        props: ['products'],
        data() {
            return {
              asin: '',
              id: '',
              reference: ''
            }
        },
        // watch: {
        //     messageAttr(newVal, oldVal) {
        //         this.text = newVal.text
        //         this.id = newVal.id
        //     }
        // },
        methods: {
            save() {
                const existingProduct = this.products.find(
                  product => product.asin === this.asin);

                // if (this.id) {
                //     this.$resource('/message{/id}').update({id: this.id}, message).then(result =>
                //         result.json().then(data => {
                //             const index = getIndex(this.messages, data.id)
                //             this.messages.splice(index, 1, data)
                //             this.text = ''
                //             this.id = ''
                //         })
                //     )
                // } else {
                //     this.$resource('/message{/id}').save({}, message).then(result =>
                //         result.json().then(data => {
                //             this.messages.push(data)
                //             this.text = ''
                //         })
                //     )
                // }
              if (existingProduct) {
                alert('Product with the same ASIN already exists!');
                this.asin = ''
              } else {
                this.$resource('/products{/asin}').save({}, this.asin).then(result =>
                    result.json().then(data => {
                      if (data.reference !== '') {
                        this.products.push(data);
                        this.asin = ''
                      } else {
                        //if returned reference to product is empty,
                        //then no product found by entered ASIN
                        alert('Product with this ASIN not found. Please enter valid ASIN');
                      }
                    }))
              }
            }
        }
    }
</script>

<style>

</style>
