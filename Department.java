package eventorganizer;

/**
 * Represents predefined departments with their full name
 * @author Jeeva Ramasamy, Parth Patel
 */
public enum Department {
    BAIT ("Business Analytics and Information Technology"),
    CS ("Computer Science"),
    EE ("Electrical Engineering"),
    ITI ("Information Technology and Informatics"),
    MATH ("Mathematics");

    private final String name;

    /**
     * Creates predefined Department objects with full name
     * @param name the full name of the department
     */
    Department(String name) {
        this.name = name;
    }

    /**
     * Returns a string representation of the department
     * @return full name of department
     */
    @Override
    public String toString() {
        return this.name;
    }
}
