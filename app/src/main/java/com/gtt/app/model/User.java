package com.gtt.app.model;

public class User {
    private Integer userId;
    private String userName;
    private String userPsw;
    private String surepassword;
    private String file;
//    private Integer province;
//    private Integer city;
//    private Integer district;

    private Integer userProvince;
    private Integer userCity;
    private Integer userDistrict;

    private String userGener;
    private String userPhone;
    private String userEmail;
    private String userAddressDetail;
    private Integer userAuthority;

    private String province;
    private String city;
    private String district;
    private String option;
    private Integer count;

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPsw() {
        return userPsw;
    }

    public void setUserPsw(String userPsw) {
        this.userPsw = userPsw;
    }

    public String getSurepassword() {
        return surepassword;
    }

    public void setSurepassword(String surepassword) {
        this.surepassword = surepassword;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getUserGener() {
        return userGener;
    }

    public void setUserGener(String userGener) {
        this.userGener = userGener;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserAddressDetail() {
        return userAddressDetail;
    }

    public void setUserAddressDetail(String userAddressDetail) {
        this.userAddressDetail = userAddressDetail;
    }

    public Integer getUserAuthority() {
        return userAuthority;
    }

    public void setUserAuthority(Integer userAuthority) {
        this.userAuthority = userAuthority;
    }

    public Integer getUserProvince() {
        return userProvince;
    }

    public void setUserProvince(Integer userProvince) {
        this.userProvince = userProvince;
    }

    public Integer getUserCity() {
        return userCity;
    }

    public void setUserCity(Integer userCity) {
        this.userCity = userCity;
    }

    public Integer getUserDistrict() {
        return userDistrict;
    }

    public void setUserDistrict(Integer userDistrict) {
        this.userDistrict = userDistrict;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", userPsw='" + userPsw + '\'' +
                ", surepassword='" + surepassword + '\'' +
                ", file='" + file + '\'' +
                ", userProvince=" + userProvince +
                ", userCity=" + userCity +
                ", userDistrict=" + userDistrict +
                ", userGener='" + userGener + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userAddressDetail='" + userAddressDetail + '\'' +
                ", userAuthority=" + userAuthority +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                '}';
    }
}
