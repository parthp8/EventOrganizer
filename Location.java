package eventorganizer;

/**
 * Represents predefined locations with the building and campus
 * @author Jeeva Ramasamy, Parth Patel
 */
public enum Location {
    ARC103 ("Allison Road Classroom", "Busch"),
    HLL114 ("Hill Center", "Busch"),
    AB2225 ("Academic Building", "College Avenue"),
    MU302 ("Murray Hall", "College Avenue"),
    BE_AUD ("Beck Hall", "Livingston"),
    TIL232 ("Tillett Hall", "Livingston");

    private final String building;
    private final String campus;

    /**
     * Creates predefined Location objects with building and campus
     * @param building the building of location
     * @param campus the campus of location
     */
    Location(String building, String campus) {
        this.building = building;
        this.campus = campus;
    }

    /**
     * Returns a string representation of the location
     * @return string in format (building name, campus name)
     */
    @Override
    public String toString() {
        return this.name() + " (" + this.building + ", " + this.campus + ")";
    }
}

