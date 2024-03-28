const express = require('express');
const router = express.Router();
const productController = require('../controllers/productcontroller');

router.get("/getListProduct", productController.getListProduct);
router.post("/addProduct", productController.addProduct);
router.put("/updateProduct/:id", productController.updateProduct);
router.delete("/deleteProduct/:id", productController.deleteProduct);
router.get("/getById/:id", productController.getProductById);
module.exports = router;