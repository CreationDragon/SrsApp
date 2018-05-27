package com.gtt.app.model;

public class Volunteer {
    private int volunteerId;
    private String volunteerName;
    private Integer volunteerAge;
    private String volunteerGender;
    private String volunteerAddress;
    private String volunteerEmail;
    private String volunteerPhone;
    private String volunteerRegisterDate;

    public int getVolunteerId() {
        return volunteerId;
    }

    public void setVolunteerId(int volunteerId) {
        this.volunteerId = volunteerId;
    }

    public String getVolunteerName() {
        return volunteerName;
    }

    public void setVolunteerName(String volunteerName) {
        this.volunteerName = volunteerName;
    }

    public Integer getVolunteerAge() {
        return volunteerAge;
    }

    public void setVolunteerAge(Integer volunteerAge) {
        this.volunteerAge = volunteerAge;
    }

    public String getVolunteerGender() {
        return volunteerGender;
    }

    public void setVolunteerGender(String volunteerGender) {
        this.volunteerGender = volunteerGender;
    }

    public String getVolunteerAddress() {
        return volunteerAddress;
    }

    public void setVolunteerAddress(String volunteerAddress) {
        this.volunteerAddress = volunteerAddress;
    }

    public String getVolunteerEmail() {
        return volunteerEmail;
    }

    public void setVolunteerEmail(String volunteerEmail) {
        this.volunteerEmail = volunteerEmail;
    }

    public String getVolunteerPhone() {
        return volunteerPhone;
    }

    public void setVolunteerPhone(String volunteerPhone) {
        this.volunteerPhone = volunteerPhone;
    }

    public String getVolunteerRegisterDate() {
        return volunteerRegisterDate;
    }

    public void setVolunteerRegisterDate(String volunteerRegisterDate) {
        this.volunteerRegisterDate = volunteerRegisterDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Volunteer volunteer = (Volunteer) o;

        if (volunteerId != volunteer.volunteerId) return false;
        if (volunteerName != null ? !volunteerName.equals(volunteer.volunteerName) : volunteer.volunteerName != null)
            return false;
        if (volunteerAge != null ? !volunteerAge.equals(volunteer.volunteerAge) : volunteer.volunteerAge != null)
            return false;
        if (volunteerGender != null ? !volunteerGender.equals(volunteer.volunteerGender) : volunteer.volunteerGender != null)
            return false;
        if (volunteerAddress != null ? !volunteerAddress.equals(volunteer.volunteerAddress) : volunteer.volunteerAddress != null)
            return false;
        if (volunteerEmail != null ? !volunteerEmail.equals(volunteer.volunteerEmail) : volunteer.volunteerEmail != null)
            return false;
        if (volunteerPhone != null ? !volunteerPhone.equals(volunteer.volunteerPhone) : volunteer.volunteerPhone != null)
            return false;
        if (volunteerRegisterDate != null ? !volunteerRegisterDate.equals(volunteer.volunteerRegisterDate) : volunteer.volunteerRegisterDate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = volunteerId;
        result = 31 * result + (volunteerName != null ? volunteerName.hashCode() : 0);
        result = 31 * result + (volunteerAge != null ? volunteerAge.hashCode() : 0);
        result = 31 * result + (volunteerGender != null ? volunteerGender.hashCode() : 0);
        result = 31 * result + (volunteerAddress != null ? volunteerAddress.hashCode() : 0);
        result = 31 * result + (volunteerEmail != null ? volunteerEmail.hashCode() : 0);
        result = 31 * result + (volunteerPhone != null ? volunteerPhone.hashCode() : 0);
        result = 31 * result + (volunteerRegisterDate != null ? volunteerRegisterDate.hashCode() : 0);
        return result;
    }
}
