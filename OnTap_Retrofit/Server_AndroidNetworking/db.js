const { render } = require('ejs')
const express = require('express')
const app = express()
app.use(express.static('public'));
const bodyParser = require("body-parser");
app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());
app.set('view engine', 'ejs')
const port = 3000
const mongoose = require('mongoose')
const Product = require("./model/products")
mongoose.connect('mongodb://localhost:27017/mydatabase', {
    useNewUrlParser: true,
    useUnifiedTopology: true
})
    .then(() => {
        console.log('Đã kết nối tới MongoDB');
    })
    .catch((error) => {
        console.error('Lỗi kết nối tới MongoDB:', error);
    });
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
app.get("/getAll/product", async (req, res) => {
    try {
        const product = await Product.find({})
        res.json(product)
    } catch (error) {
        console.error("Error fetching product:", error);
        res.status(500).send("Internal Server Error");
    }
});
app.get("/getproduct/:id", async (req, res) => {
    const productId = req.params.id;
    try {
        const product = await Product.findById(productId)
        res.json(product)
    } catch (error) {
        console.error(error);
        res.status(500).send('Internal Server Error');
    }
})
app.delete('/deleteProduct/:id', async (req, res) => {
    const productId = req.params.id;

    try {
        const result = await Product.findByIdAndDelete(productId);

        if (result) {

            res.json({ message: `Product with id ${productId} deleted successfully.` });
        } else {

            res.status(404).json({ error: `Product with id ${productId} not found.` });
        }
    } catch (error) {

        console.error('Error deleting product:', error);
        res.status(500).json({ error: 'Internal Server Error' });
    }

});
app.put('/editProduct/Product/:id', async (req, res) => {

    try {
        const { name, price, brand } = req.body;
        const updatedProduct = await Product.findOneAndUpdate(
            { _id: req.params.id },
            { $set: { name, price, brand } },
            { new: true }
        );

        if (!updatedProduct) {
            return res.status(404).json({ message: 'Product not found' });
        }

        res.status(200).json(updatedProduct);
    } catch (error) {
        console.error(error);
        res.status(500).send('Internal Server Error');
    }
});
app.listen(port, () => {
    console.log(`Server đang lắng nghe tại cổng ${port}`);
});
