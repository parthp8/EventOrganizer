package eventorganizer;

import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Processes user input and runs program
 * @author Jeeva Ramasamy, Parth Patel
 */
public class EventOrganizer {

    private static final String ADD = "A";
    private static final String REMOVE = "R";
    private static final String PRINT = "P";
    private static final String PRINT_BY_DATE = "PE";
    private static final String PRINT_BY_CAMPUS = "PC";
    private static final String PRINT_BY_DEPARTMENT = "PD";
    private static final String QUIT = "Q";

    private static final int MIN_DURATION = 30;
    private static final int MAX_DURATION = 120;


    /**
     * Runs the program
     */
    public void run() {
        System.out.println("Event Organizer running...\n");
        EventCalendar calendar = new EventCalendar();
        Scanner input = new Scanner(System.in);
        boolean isRunning = true;
        while (input.hasNextLine() && isRunning) {
            String line = input.nextLine();
            while (line.isEmpty())
                line = input.nextLine();
            StringTokenizer st = new StringTokenizer(line);
            String command = st.nextToken();
            switch (command) {
                case ADD:
                    addEvent(calendar, st);
                    break;
                case REMOVE:
                    removeEvent(calendar, st);
                    break;
                case PRINT:
                    calendar.print();
                    break;
                case PRINT_BY_DATE:
                    calendar.printByDate();
                    break;
                case PRINT_BY_CAMPUS:
                    calendar.printByCampus();
                    break;
                case PRINT_BY_DEPARTMENT:
                    calendar.printByDepartment();
                    break;
                case QUIT:
                    isRunning = false;
                    System.out.println("Event Organizer terminated.");
                    break;
                default:
                    System.out.println(command + " is an invalid command!");
            }
        }
    }

    /**
     * Adds the event to calendar if details are valid
     * @param calendar the current calendar of events
     * @param st list of parameters
     */
    private void addEvent(EventCalendar calendar, StringTokenizer st) {
        StringTokenizer dateTokens =
                new StringTokenizer(st.nextToken(), "/");
        int month = Integer.parseInt(dateTokens.nextToken()),
                day = Integer.parseInt(dateTokens.nextToken()),
                year = Integer.parseInt(dateTokens.nextToken());
        Date date = new Date(year, month, day);
        Timeslot startTime = findTimeSlot(st.nextToken().toUpperCase());
        Location location = findLocation(st.nextToken().toUpperCase());
        Department department = findDepartment(st.nextToken().toUpperCase());
        String email = st.nextToken();
        Contact contact = new Contact(department, email);
        int duration = Integer.parseInt(st.nextToken());
        if (!checkValidity(date, startTime, location,
                department, contact, duration))
            return;

        Event event = new Event(date, startTime, location,
                contact, duration);
        if (calendar.contains(event)) {
            System.out.println("The event is already on the calendar.");
            return;
        }
        calendar.add(event);
        System.out.println("Event added to the calendar.");
    }

    /**
     * Removes the event from calendar if details are valid
     * @param calendar the current calendar of events
     * @param st list of parameters
     */
    private void removeEvent(EventCalendar calendar, StringTokenizer st) {
        StringTokenizer dateTokens =
                new StringTokenizer(st.nextToken(), "/");
        int month = Integer.parseInt(dateTokens.nextToken()),
                day = Integer.parseInt(dateTokens.nextToken()),
                year = Integer.parseInt(dateTokens.nextToken());
        Date date = new Date(year, month, day);
        Timeslot startTime = findTimeSlot(st.nextToken().toUpperCase());
        Location location = findLocation(st.nextToken().toUpperCase());
        if (!checkValidity(date, startTime, location))
            return;

        Event event = new Event(date, startTime, location);
        if (calendar.remove(event)) {
            System.out.println("Event has been removed from the calendar!");
        }
        else {
            System.out.println("Cannot remove; event is not "
                    + "in the calendar!");
        }
    }

    /**
     * Returns enum timeslot that corresponds to input timeslot
     * if there is one
     * @param timeslot the input timeslot
     * @return enum timeslot if it exists, null otherwise
     */
    private Timeslot findTimeSlot(String timeslot) {
        for (Timeslot ts: Timeslot.values()) {
            if (ts.name().equals(timeslot)) {
                return ts;
            }
        }
        return null;
    }

    /**
     * Returns enum location that corresponds to input location
     * if there is one
     * @param location the input location
     * @return enum location if it exists, null otherwise
     */
    private Location findLocation(String location) {
        for (Location loc: Location.values()) {
            if (loc.name().equals(location)) {
                return loc;
            }
        }
        return null;
    }

    /**
     * Returns enum department that corresponds to input department
     * if there is one
     * @param department the input department
     * @return enum department if it exists, null otherwise
     */
    private Department findDepartment(String department) {
        for (Department dep: Department.values()) {
            if (dep.name().equals(department)) {
                return dep;
            }
        }
        return null;
    }

    /**
     * Checks if the parameters are valid entries for a calendar event
     * Prints error statement if there are invalid entries
     * @param date the date of the event
     * @param tSlot the time slot of the event
     * @param loc the location of the event
     * @return true if valid entries, false otherwise
     */
    private boolean checkValidity(Date date, Timeslot tSlot, Location loc) {
        if (!date.isValid()) {
            System.out.println(date + ": Invalid calendar date!");
            return false;
        }
        if (!date.isFutureDate()) {
            System.out.println(date + ": Event date must be a future date!");
            return false;
        }
        if (!date.isWithinSixMonths()) {
            System.out.println(date + ": Event date must be "
                    + "within 6 months!");
            return false;
        }
        if (tSlot == null) {
            System.out.println("Invalid time slot!");
            return false;
        }
        if (loc == null) {
            System.out.println("Invalid location!");
            return false;
        }
        return true;
    }

    /**
     * Checks if the parameters are valid entries for a calendar event
     * Prints error statement if there are invalid entries
     * @param date the date of the event
     * @param tSlot the time slot of the event
     * @param loc the location of the event
     * @param dep the department of the event
     * @param contact the contact for the event
     * @param dur the duration of the event
     * @return true if valid entries, false otherwise
     */
    private boolean checkValidity(Date date, Timeslot tSlot, Location loc,
                                  Department dep, Contact contact, int dur) {
        if (!checkValidity(date, tSlot, loc)) {
            return false;
        }
        if (dep == null || !contact.isValid()) {
            System.out.println("Invalid contact information!");
            return false;
        }
        if (dur < MIN_DURATION || dur > MAX_DURATION) {
            System.out.println("Event duration must be at least "
                    + MIN_DURATION + " minutes and at most "
                    + MAX_DURATION + " minutes");
            return false;
        }
        return true;
    }
}
