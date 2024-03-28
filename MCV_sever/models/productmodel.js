const db = require('./db');

const productSchema = new db.mongoose.Schema({
    name: { type: String, require: true },
    price: { type: String },
    brand: { type: String },
},
    { collection: "products" }
);

const productModel = db.mongoose.model("productModel", productSchema);
module.exports = { productModel };