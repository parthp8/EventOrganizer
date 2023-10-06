package eventorganizer;

/**
 * Represents an event with a date, start time, location, contact,
 * and duration
 * @author Jeeva Ramasamy, Parth Patel
 */
public class Event implements Comparable<Event> {
    private Date date; //the event date
    private Timeslot startTime; //the starting time
    private Location location;
    private Contact contact; //include the department name and email
    private int duration; //in minutes

    private static final int GREATER_THAN = 1;
    private static final int LESS_THAN = -1;
    private static final int EQUAL = 0;

    private static final int MAX_HOUR = 12;
    private static final int MINUTES_IN_HOUR = 60;
    private static final int SMALLEST_DOUBLE_DIGIT_NUM = 10;

    /**
     * Creates an Event object with the specified date, startTime, location,
     * contact, and duration
     * @param date the date of the event
     * @param startTime the start time of the event
     * @param location the location of the event
     * @param contact the contact for the event
     * @param duration the duration of the event
     */
    public Event(Date date, Timeslot startTime, Location location,
                 Contact contact, int duration) {
        this.date = date;
        this.startTime = startTime;
        this.location = location;
        this.contact = contact;
        this.duration = duration;
    }

    /**
     * Creates an Event object with the specified date, startTime,
     * and location
     * @param date the date of the event
     * @param startTime the start time of the event
     * @param location the location of the event
     */
    public Event(Date date, Timeslot startTime, Location location) {
        this.date = date;
        this.startTime = startTime;
        this.location = location;
    }

    /**
     * Returns the location that this event takes place in
     * @return location
     */
    public Location getLocation() {
        return this.location;
    }

    /**
     * Returns the department that this event takes place in
     * @return department
     */
    public Department getDepartment() {
        return this.contact.getDepartment();
    }

    /**
     * Checks if this event is equal to the specified object
     * @param  obj the specified object
     * @return true if equal, false if not
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Event) {
            Event event = (Event) obj;
            return this.date.compareTo(event.date) == EQUAL
                    && this.startTime.equals(event.startTime)
                    && this.location.equals(event.location);
        }
        return false;
    }

    /**
     * Returns a string representation of the event
     * @return string version of event in format
     *         [Event Date: date] [Start: start time]
     *         [End: end time] @location [Contact: contact]
     */
    @Override
    public String toString() {
        int endHour = this.startTime.getHour();
        int endMinute = this.startTime.getMinute();
        boolean isAM = this.startTime.isAM();

        endMinute += this.duration;
        while (endMinute >= MINUTES_IN_HOUR) {
            ++endHour;
            endMinute -= MINUTES_IN_HOUR;
            if (endHour >= MAX_HOUR) {
                isAM = false;
            }
        }

        String endTime = endHour + ":";
        if (endMinute < SMALLEST_DOUBLE_DIGIT_NUM)
            endTime += "0";
        endTime += endMinute;
        endTime += isAM ? "am" : "pm";

        String output = "[Event Date: " + this.date + "] ";
        output += "[Start: " + this.startTime + "] ";
        output += "[End: " + endTime + "] ";
        output += "@" + this.location + " ";
        output += "[Contact: " + this.contact + "]";
        return output;
    }

    /**
     * Compares this event with the specified event
     * @param  event the object to be compared.
     * @return 1 if this event occurs after the specified event,
     *         -1 if this event occurs before the specified event,
     *         0 if the events occur at the same time
     */
    @Override
    public int compareTo(Event event) {
        if (this.date.compareTo(event.date) > 0) {
            return GREATER_THAN;
        }
        else if (this.date.compareTo(event.date) < 0) {
            return LESS_THAN;
        }
        else {
            if (this.startTime.compareTo(event.startTime) > 0) {
                return GREATER_THAN;
            } else if (this.startTime.compareTo(event.startTime) < 0) {
                return LESS_THAN;
            } else {
                return EQUAL;
            }
        }
    }

    /**
     * Test case #1
     * Tests if two events with same date, start time, and location are equal
     */
    private static void testEqualEvents() {
        System.out.println("** Test case #1: date, start time,"
                + " and location are equal");
        Date date = new Date(2023, 10, 23);
        Timeslot ts = Timeslot.AFTERNOON;
        Location loc = Location.HLL114;
        Event event1 = new Event(date, ts, loc);
        Event event2 = new Event(date, ts, loc);
        boolean expectedOutput = true, actualOutput = event1.equals(event2);
        testResult(event1, event2, expectedOutput, actualOutput);
    }

    /**
     * Test case #2
     * Tests if two events with same start time and location,
     * but different dates are equal
     */
    private static void testEqualTimeslot_And_Loc() {
        System.out.println("** Test case #2: start time and location"
                + " are equal, date is different");
        Date date1 = new Date(2023, 11, 7);
        Date date2 = new Date(2024, 1, 14);
        Timeslot ts = Timeslot.MORNING;
        Location loc = Location.AB2225;
        Event event1 = new Event(date1, ts, loc);
        Event event2 = new Event(date2, ts, loc);
        boolean expectedOutput = false, actualOutput = event1.equals(event2);
        testResult(event1, event2, expectedOutput, actualOutput);
    }

    /**
     * Test case #3
     * Tests if two events with same date and location,
     * but different start times are equal
     */
    private static void testEqualDate_And_Loc() {
        System.out.println("** Test case #3: date and location are equal, "
                + "start time is different");
        Date date = new Date(2023, 8, 29);
        Timeslot ts1 = Timeslot.MORNING;
        Timeslot ts2 = Timeslot.EVENING;
        Location loc = Location.BE_AUD;
        Event event1 = new Event(date, ts1, loc);
        Event event2 = new Event(date, ts2, loc);
        boolean expectedOutput = false, actualOutput = event1.equals(event2);
        testResult(event1, event2, expectedOutput, actualOutput);
    }

    /**
     * Test case #4
     * Tests if two events with same date and start time,
     * but different locations are equal
     */
    private static void testEqualDate_And_Timeslot() {
        System.out.println("** Test case #4: date and timeslot are equal, "
                + "location is different");
        Date date = new Date(2023, 10, 31);
        Timeslot ts = Timeslot.EVENING;
        Location loc1 = Location.MU302;
        Location loc2 = Location.TIL232;
        Event event1 = new Event(date, ts, loc1);
        Event event2 = new Event(date, ts, loc2);
        boolean expectedOutput = false, actualOutput = event1.equals(event2);
        testResult(event1, event2, expectedOutput, actualOutput);
    }

    /**
     * Prints the result from the test case whether it passes or fails
     * @param event1 the event being tested
     * @param event2 the event being compared to
     * @param expectedOutput this is what the test case should return
     * @param actualOutput this is what the test case returned
     */
    private static void testResult(Event event1, Event event2,
                                   boolean expectedOutput,
                                   boolean actualOutput) {
        System.out.println("Test Input 1: " + event1);
        System.out.println("Test Input 2: " + event2);
        System.out.println("Expected Input: " + expectedOutput
                + "  vs  " + "Actual Input: " + actualOutput);
        System.out.println((expectedOutput == actualOutput) ?
                "(PASS)" : "(FAIL)");
    }

    /**
     * Testbed main used as the driver to test equals()
     * @param args command line arguments
     */
    public static void main(String[] args) {
        testEqualEvents();
        testEqualTimeslot_And_Loc();
        testEqualDate_And_Loc();
        testEqualDate_And_Timeslot();
    }
}