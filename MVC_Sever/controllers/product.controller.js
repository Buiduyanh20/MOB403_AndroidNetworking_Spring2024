const { productModel } = require("../models/product.model");

exports.getListProduct = async (req, res, next) => {
  try {
    let list = await productModel.find();
    res.json(list);
  } catch (error) {
    req.json({ status: "not found", error: error });
  }
};

// add product
exports.addProduct = async (req, res, next) => {
  try {
    let newProductModel = new productModel({
      name: req.body.name,
      price: req.body.price,
      brand: req.body.brand,
    });
    let obj = await newProduct.save();
    res.json({ status: "Thêm thành công", result: obj });
  } catch (error) {
    req.json({ status: "Không thành công", result: error });
  }
};

// update product
exports.updateProduct = (req, res, next) => {
  try {
    let id = req.params.id;
    let obj = {
      name: req.body.name,
      price: req.body.price,
      brand: req.body.brand,
    };
    let newObj = productModel.findByIDAndUpdate(id, obj);
    res.json({ status: "Update thành công", result: newObj });
  } catch (error) {
    res.json({ status: " Lỗi Update", result: error });
  }
};
