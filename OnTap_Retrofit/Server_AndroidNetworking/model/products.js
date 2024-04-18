const mongoose = require('mongoose')
const ProductSchema = new mongoose.Schema({
    name: String,
    price: Number,
    brand: String,

});

const Product = mongoose.model("Products", ProductSchema);
module.exports = Product;