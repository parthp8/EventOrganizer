package eventorganizer;

/**
 * Represents predefined timeslots with their start time
 * @author Jeeva Ramasamy, Parth Patel
 */
public enum Timeslot {
    MORNING (10, 30, "am"),
    AFTERNOON (2, 0, "pm"),
    EVENING (6, 30, "pm");

    private final int hour;
    private final int minute;
    private final String am_pm;

    /**
     * Creates a predefined timeslot with hour, minute, and am/pm
     * @param hour the hour at which the timeslot starts
     * @param minute the minute at which the timeslot starts
     * @param am_pm string "am" or "pm"
     */
    Timeslot(int hour, int minute, String am_pm) {
        this.hour = hour;
        this.minute = minute;
        this.am_pm = am_pm;
    }

    /**
     * Returns the hour at which the timeslot starts
     * @return hour
     */
    public int getHour() {
        return this.hour;
    }

    /**
     * Returns the minute at which the timeslot starts
     * @return minute
     */
    public int getMinute() {
        return this.minute;
    }

    /**
     * Checks whether the timeslot is AM or not
     * @return true if AM, false if not
     */
    public boolean isAM() {
        return this.am_pm.equals("am");
    }

    /**
     * Returns a string representation of the timeslot
     * @return string in format (h)h:mm(am/pm)
     */
    @Override
    public String toString() {
        String min = (this.minute == 0) ? "00" : "" + this.minute;
        return this.hour + ":" + min + this.am_pm;
    }
}
