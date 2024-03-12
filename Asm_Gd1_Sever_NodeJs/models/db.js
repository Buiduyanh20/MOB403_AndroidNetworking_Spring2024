const mongoose = require("mongoose");

mongoose.connect("mongodb://127.0.0.1:27017/DuyAnhAsm").catch((err) => {
  console.error("Lỗi kết nối CSDL\n" + err);
});

module.exports = { mongoose };
