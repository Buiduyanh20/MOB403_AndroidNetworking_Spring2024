const db = require("./db");
const productSchema = new db.Schema(
  {
    name: { type: String, require: true },
    price: { type: Number, default: 0 },
    brand: { type: String },
  },
  {
    collection: "products",
  }
);
const productModel = db.model("productModel", productSchema);
module.exports = { productModel };
