package max.com.taxipass.model;


public class User {
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private Integer complaintCount;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getComplaintCount() {
        return complaintCount;
    }

    public  void setComplaintCount(int complaintCount) {
        this.complaintCount = complaintCount;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}