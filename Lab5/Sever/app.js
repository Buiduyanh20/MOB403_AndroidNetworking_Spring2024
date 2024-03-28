const express = require("express");
const mongoose = require("mongoose");

const CarModel = require("./model/CarModel");
const uri = "mongodb://127.0.0.1:27017/Lab5_NWK";
const bodyParser = require("body-parser");

mongoose
  .connect(uri, {
    useNewUrlParser: true,
    useUnifiedTopology: true,
  })
  .then(() => {
    console.log("Da ket noi voi MongoDB");
  })
  .catch((err) => {
    console.error("Khong ket noi dc MongoDB: ", err);
  });

const app = express();
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

app.get("/listCar", async function (req, res) {
  try {
    console.log("Da ket noi voi MongoDB");
    getListCarAndRespone(res);
  } catch (err) {
    console.error("Error fetching products:", err);
    res.status(500).json({ error: "Internal server error" });
  }
});
async function getListCarAndRespone(res) {
  const carList = await CarModel.find().lean();
  res.json(carList);
}

// Định nghĩa API thêm sản phẩm
app.post("/addCars", async (req, res) => {
  try {
    const { name, price, color } = req.body;
    const car = new CarModel({
      name,
      price,
      color,
    });
    await car.save();
    getListCarAndRespone(res);
  } catch (err) {
    console.error("Error adding product:", err);
    res.status(500).json({ error: "Internal server error" });
  }
});

// Định nghĩa API sửa đổi sản phẩm
app.put("/cars/:carId", async (req, res) => {
  try {
    const { carId } = req.params;
    const { name, price, color } = req.body;
    const car = await CarModel.findByIdAndUpdate(
      carId,
      { name, price, color },
      { new: true }
    );
    getListCarAndRespone(res);
  } catch (err) {
    console.error("Error updating product:", err);
    res.status(500).json({ error: "Internal server error" });
  }
});

// Định nghĩa API xóa sản phẩm
app.delete("/cars/:carId", async (req, res) => {
  try {
    const { carId } = req.params;
    await CarModel.findByIdAndRemove(carId);
    getListCarAndRespone(res);
  } catch (err) {
    console.error("Error deleting product:", err);
    res.status(500).json({ error: "Internal server error" });
  }
});

// Khởi động server
const port = 8000;
app.listen(port, () => {
  console.log(`Server started on port ${port}`);
});
