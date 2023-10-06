package eventorganizer;

/**
 * Represents a contact with department and email
 * @author Jeeva Ramasamy, Parth Patel
 */
public class Contact {
    private Department department;
    private String email;

    /**
     * Creates a Contact object with the specified department and email
     * @param department the department of the contact
     * @param email the email of the contact
     */
    public Contact(Department department, String email) {
        this.department = department;
        this.email = email;
    }

    /**
     * Returns the department of this contact
     * @return department
     */
    public Department getDepartment() {
        return this.department;
    }

    /**
     * Checks if the department name and email address are valid
     * @return true if valid, false otherwise
     */
    public boolean isValid() {
        if (!this.email.endsWith("@rutgers.edu"))
            return false;

        for (Department department: Department.values()) {
            if (this.department.name().equals(department.name()))
                return true;
        }
        return false;
    }

    /**
     * Returns a string representation of the contact
     * @return string version of contact of format department, email
     */
    @Override
    public String toString() {
        return this.department + ", " + this.email;
    }
}
