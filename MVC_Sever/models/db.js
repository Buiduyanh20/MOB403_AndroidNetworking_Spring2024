const mongoose = require("mongoose");

mongoose
  .connect("mongodb://127.0.0.1:27017/MVC_Sever", {
    useNewUrlParser: true,
    useUnifiedTopology: true,
  })
  .then(() => {
    console.log("Kết nối tới MongoDB thành công.");
  })
  .catch((err) => {
    console.error("Lỗi khi kết nối tới MongoDB: " + err);
  });
