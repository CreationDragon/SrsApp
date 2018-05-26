package com.gtt.app.entity;
public class User {
    private int userId;
    private String userName;
    private String userPsw;
    private String userGener;
    private String userPhone;
    private String userEmail;
    private Integer userAuthority;
    private String userAddressDetail;
    private String userHead;
    private Integer userProvince;
    private Integer userCity;
    private Integer userDistrict;
    private Integer userState;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
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

    public Integer getUserAuthority() {
        return userAuthority;
    }

    public void setUserAuthority(Integer userAuthority) {
        this.userAuthority = userAuthority;
    }

    public String getUserAddressDetail() {
        return userAddressDetail;
    }

    public void setUserAddressDetail(String userAddressDetail) {
        this.userAddressDetail = userAddressDetail;
    }

    public String getUserHead() {
        return userHead;
    }

    public void setUserHead(String userHead) {
        this.userHead = userHead;
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

    public Integer getUserState() {
        return userState;
    }

    public void setUserState(Integer userState) {
        this.userState = userState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (userId != user.userId) return false;
        if (userName != null ? !userName.equals(user.userName) : user.userName != null) return false;
        if (userPsw != null ? !userPsw.equals(user.userPsw) : user.userPsw != null) return false;
        if (userGener != null ? !userGener.equals(user.userGener) : user.userGener != null) return false;
        if (userPhone != null ? !userPhone.equals(user.userPhone) : user.userPhone != null) return false;
        if (userEmail != null ? !userEmail.equals(user.userEmail) : user.userEmail != null) return false;
        if (userAuthority != null ? !userAuthority.equals(user.userAuthority) : user.userAuthority != null)
            return false;
        if (userAddressDetail != null ? !userAddressDetail.equals(user.userAddressDetail) : user.userAddressDetail != null)
            return false;
        if (userHead != null ? !userHead.equals(user.userHead) : user.userHead != null) return false;
        if (userProvince != null ? !userProvince.equals(user.userProvince) : user.userProvince != null) return false;
        if (userCity != null ? !userCity.equals(user.userCity) : user.userCity != null) return false;
        if (userDistrict != null ? !userDistrict.equals(user.userDistrict) : user.userDistrict != null) return false;
        if (userState != null ? !userState.equals(user.userState) : user.userState != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (userPsw != null ? userPsw.hashCode() : 0);
        result = 31 * result + (userGener != null ? userGener.hashCode() : 0);
        result = 31 * result + (userPhone != null ? userPhone.hashCode() : 0);
        result = 31 * result + (userEmail != null ? userEmail.hashCode() : 0);
        result = 31 * result + (userAuthority != null ? userAuthority.hashCode() : 0);
        result = 31 * result + (userAddressDetail != null ? userAddressDetail.hashCode() : 0);
        result = 31 * result + (userHead != null ? userHead.hashCode() : 0);
        result = 31 * result + (userProvince != null ? userProvince.hashCode() : 0);
        result = 31 * result + (userCity != null ? userCity.hashCode() : 0);
        result = 31 * result + (userDistrict != null ? userDistrict.hashCode() : 0);
        result = 31 * result + (userState != null ? userState.hashCode() : 0);
        return result;
    }
}
