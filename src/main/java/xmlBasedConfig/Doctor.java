package xmlBasedConfig;

public class Doctor implements  Staff{
    private String qualification;
    Branch branch;
    public void assist () {
        System.out.println("Doctor assist");
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }
}
