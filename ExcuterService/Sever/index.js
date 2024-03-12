const express = require("express");

const mongoose = require("mongoose");
const port = 3000;
const app = express();

const bodyParser = require("body-parser");
app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());

// Kết nối tới MongoDB_
mongoose
  .connect("mongodb://localhost:27017/Lab2", {
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
const userSchema = new mongoose.Schema({
  name: String,
  price: String,
  brand: String,
});
const Product = mongoose.model("product", userSchema);
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

app.get("/users", async (req, res) => {
  //lấy tất cả dữ liệu từ MongoDB
  try {
    const users = await Product.find({});
    res.json(users);
  } catch (err) {
    console.error("Error fetching users:", err);
    res.status(500).send("Internal Server Error");
  }
});

// Update product
app.put("/product/update/:id", (req, res) => {
  const productId = req.params.id;
  const updateData = {
    name: req.body.name,
    price: req.body.price,
    brand: req.body.brand,
  };

  Product.findByIdAndUpdate(productId, updateData)
    .then(() => {
      res.status(200).json({ message: "Cập nhật thành công" });
    })
    .catch((error) => {
      res.status(500).json({ error: "Lỗi cập nhật" });
    });
});

app.delete("/delete/:id", (rep, res) => {
  const id = rep.params.id;
  Product.findByIdAndDelete(id)
    .then(() => {
      res.status(200).json({ message: "xoá thành công" });
    })
    .catch((err) => {
      res.status(400).json({ err: "lỗi" });
    });
});

// Get product by ID
app.get("/product/:id", (req, res) => {
  const productId = req.params.id;

  Product.findById(productId)
    .then((product) => {
      if (!product) {
        return res.status(404).json({ message: "Không tìm thấy sản phẩm" });
      }
      res.status(200).json(product);
    })
    .catch((error) => {
      res.status(500).json({ error: "Lỗi khi lấy sản phẩm" });
    });
});
