const express = require("express");
const mongoose = require("mongoose");
const port = 3000;
const app = express();
const bodyParser = require("body-parser");
app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());
// Kết nối tới MongoDB
mongoose
  .connect("mongodb://localhost:27017/Thi17_PH30569", {
    useNewUrlParser: true,
    useUnifiedTopology: true,
  })
  .then(() => {
    console.log("Đã kết nối tới MongoDB");
  })
  .catch((error) => {
    console.error("Lỗi kết nối tới MongoDB:", error);
  });

const itemSchema = new mongoose.Schema({
  manv: String,
  name: String,
  email: String,
  address: String,
  image: String,
  age: String,
});
const NhanVien = mongoose.model("NhanVien", itemSchema);

app.post("/item/post", (req, res) => {
  const newData = new NhanVien({
    manv: req.body.manv,
    name: req.body.name,
    email: req.body.email,
    address: req.body.address,
    image: req.body.image,
    age: req.body.age,
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
app.get("/item/get", async (req, res) => {
  try {
    const items = await NhanVien.find();
    res.json(items);
  } catch (error) {
    console.error("Error fetching products:", error);
    res.status(500).send("Error fetching products from the database.");
  }
});
app.get("/item/get/:id", async (req, res) => {
  const itemId = req.params.id;
  try {
    const item = await NhanVien.findById(itemId);

    if (!item) {
      return res.status(404).json({ error: "Product not found" });
    }

    res.json(item);
  } catch (error) {
    console.error("Error fetching product by ID:", error);
    res
      .status(500)
      .json({ error: "Error fetching product by ID from the database." });
  }
});
app.put("/item/update/:id", async (req, res) => {
  const itemId = req.params.id;
  try {
    const updateItem = await NhanVien.findByIdAndUpdate(
      itemId,
      {
        manv: req.body.manv,
        name: req.body.name,
        email: req.body.email,
        address: req.body.address,
        image: req.body.image,
        age: req.body.age,
      },
      { new: true }
    );

    if (!updateItem) {
      return res.status(404).json({ error: "Product not found" });
    }

    res.json({ message: "Update thành công", updateItem });
  } catch (error) {
    console.error("Error updating product:", error);
    res.status(500).json({ error: "Update không thành công" });
  }
});

app.delete("/item/delete/:id", async (req, res) => {
  const itemId = req.params.id;
  try {
    const deleteItem = await NhanVien.findByIdAndDelete(itemId);

    if (!deleteItem) {
      return res.status(404).json({ error: "Product not found" });
    }

    res.json({ message: "Xóa thành công", deleteItem });
  } catch (error) {
    console.error("Error deleting product:", error);
    res.status(500).json({ error: "Xóa không thành công" });
  }
});
app.get("/api/items", async (req, res) => {
  try {
    const items = await NhanVien.find({});
    res.json(items);
  } catch (err) {
    console.error("Error fetching comics:", err);
    res.status(500).send("Internal Server Error");
  }
});

app.listen(port, () => {
  console.log(`Server đang lắng nghe tại cổng ${port}`);
});
