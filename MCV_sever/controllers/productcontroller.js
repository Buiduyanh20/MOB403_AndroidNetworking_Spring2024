const {productModel} = require('../models/productmodel');
const { param } = require('../routes');

//getlist: Lấy danh sách sản phẩm
exports.getListProduct = async (req, res, next) =>{
    try{
        let list = await productModel.find({});
        res.json(list);
    }catch(err){
        res.json({status: "Không tìm thấy!", error: err})
    }
};

//post: Them san pham
exports.addProduct = async (req, res, next) =>{
    try{
        let newProduct = new productModel({
            name: req.body.name,
            price: req.body.price,
            brand: req.body.brand,
        });
        let obj = await newProduct.save();
        if(obj){
            return res.status(200).json({ message: "Thêm sản phẩm thành công"});
        }else{
            return res.status(404).json({ message: "Thêm sản phẩm thất bại" });
        }
        
    }catch(err){
        res.json({status: "Xảy ra lỗi khi thêm sản phẩm:", result: err});
    }
}

exports.updateProduct = async (req, res, next) => {
    try {
        let id = req.params.id; // Sửa từ req.param.id thành req.params.id
        let product = {
            name: req.body.name,
            price: req.body.price,
            brand: req.body.brand,
        };
        // Sử dụng await để đợi cho phương thức findByIdAndUpdate() hoàn thành và gán kết quả cho biến updatedProduct
        let updatedProduct = await productModel.findByIdAndUpdate(id, product);
        if (updatedProduct) {
            return res.status(200).json({ message: "Cập nhật thành công item có id: " + id });
        } else {
            return res.status(404).json({ message: "Không tìm thấy sản phẩm để cập nhật" });
        }
    } catch (error) {
        console.log(error);
        return res.status(500).json({
            msg: "Cập nhật thất bại",
            success: false
        });
    }
};

exports.deleteProduct = async (req, res, nexy) =>{
    try{
        let result = await productModel.findByIdAndDelete(req.params.id);
        if (result) {
            return res.status(200).json({ message: "Xóa thành công item: " + req.params.id });
        } else {
            return res.status(404).json({ message: "Xóa thất bại" });
        }
    }catch(error){
        console.log(error);
        return res.status(500).json({
            msg: "Xóa thất bại",
            success: false
        });
    }
}

exports.getProductById = async(req, res, next) =>{
    let obj = await productModel.findById(req.params.id);
    try{
        if(obj){
            res.json({
                status: "Đã tìm thấy sản phẩm!",
                return: obj
            })
        }else{
            res.json({
                data: null,
                status:"Không tìm thấy sản phẩm!"
            })
        }
    }catch(error){
        console.log(error);
        return res.status(500).json({
            msg: "Lỗi xảy ra khi tìm kiểm sản phẩm theo id",
            success: false
        });
    }
}