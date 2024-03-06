const express = require("express");
const mongoose = require("mongoose");

const port = 3000;
const app = express();

// Kết nối tới MongoDB
mongoose
  .connect("mongodb://localhost:27017/Lesson1", {
    useNewUrlParser: true,
    useUnifiedTopology: true,
  })
  .then(() => {
    console.log("Đã kết nối tới MongoDB");
  })
  .catch((error) => {
    console.error("Lỗi kết nối tới MongoDB:", error);
  });

app.listen(port, () => {
  console.log(`Server đang lắng nghe tại cổng ${port}`);
});

// Định nghĩa Schema và Model cho collection trong MongoDB
const productSchema = new mongoose.Schema({
  name: String,
  price: String,
  brand: String,
});

const Product = mongoose.model("Product", productSchema);

const bodyParser = require("body-parser");
app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());

app.post("/product/post", (req, res) => {
  console.log(req.body.brand);
  const newData = new Product({
    name: req.body.name,
    price: req.body.price,
    brand: req.body.brand,
  });
  newData
    .save()
    .then(() => {
      res.status(200).json({ message: "post thành công" });
    })
    .catch((error) => {
      res.status(500).json({ error: "post không thành công" });
    });
});
