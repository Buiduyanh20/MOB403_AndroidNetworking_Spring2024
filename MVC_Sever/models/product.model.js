const db = require("./db");
const productSchema = new db.mongoose.Schema(
  {
    name: { type: String, require: true },
    price: { type: String, default: 0 },
    brand: { type: String },
  },
  {
    collection: "products",
  }
);
const productModel = db.model("productModel", productSchema);
module.exports = { productModel };
