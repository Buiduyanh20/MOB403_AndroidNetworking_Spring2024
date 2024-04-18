package buiduyanh.fpolyhn.thi17_ph30569;

public class NhanVienPH30569 {

    private String _id;
    private String manv;
    private String name;
    private String email;
    private String address;
    private String image;
    private String age;

    public NhanVienPH30569() {
    }

    public NhanVienPH30569(String _id, String manv, String name, String email, String address, String image, String age) {
        this._id = _id;
        this.manv = manv;
        this.name = name;
        this.email = email;
        this.address = address;
        this.image = image;
        this.age = age;
    }

    public NhanVienPH30569(String manv, String name, String email, String address, String image, String age) {
        this.manv = manv;
        this.name = name;
        this.email = email;
        this.address = address;
        this.image = image;
        this.age = age;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getManv() {
        return manv;
    }

    public void setManv(String manv) {
        this.manv = manv;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }


}
