var app = new Vue({
    el: '#app',
    data: {
        recentBlocks:[]
    },
    computed:{
        showRecentBlocks(){
            var now = Date.now();
            this.recentBlocks.forEach(block => {
                block.showtime = parseInt((now - block.time)/1000/60);
                block.showSizeOnDisk = block.sizeOnDisk.toLocaleString('en');
            });
            return this.recentBlocks;
        }
    },
    mounted() {
        console.log('view mounted');
        this.getBlockRecent();
    },
    methods: {
        getBlockRecent() {
            axios.get('/block/getRecentBlocks')
                .then(function (response) {
                    // handle success
                    console.log(response);
                    app.recentBlocks = response.data;

                })
                .catch(function (error) {
                    // handle error
                    console.log(error);
                });
        },
        handleClick(val){
            console.log(val);
        }
    }
})