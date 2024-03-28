const mongoose = require('mongoose');
// mongoose.connect("mongodb://localhost:27017/MD18201");

mongoose
.connect('mongodb://localhost:27017/MD18201', {
    useNewUrlParser: true,
    useUnifiedTopology: true
    })
    .then(() => {
        console.log('Đã kết nối tới MongoDB');
    })
    .catch((error) => {
        console.error('Lỗi kết nối tới MongoDB:', error);
    });

module.exports = mongoose;