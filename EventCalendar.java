package eventorganizer;

/**
 * Represents an organized list of events
 * @author Jeeva Ramasamy, Parth Patel
 */
public class EventCalendar {
    private Event[] events; // the array holding the list of events
    private int numEvents; // current number of events in the array

    private static final int NOT_FOUND = -1;
    private static final int INITIAL_CAPACITY = 4;
    private static final int CAPACITY_INCREASE = 4;
    private static final int EMPTY = 0;


    /**
     * Creates an empty list of events with an initial capacity of 4
     */
    public EventCalendar() {
        events = new Event[INITIAL_CAPACITY];
        numEvents = EMPTY;
    }

    /**
     * Searches for an event in the list
     * @param  event the event to be searched
     * @return the index of event if found, -1 if not found
     */
    private int find(Event event) {
        for (int i = 0; i < numEvents; ++i) {
            if (events[i].equals(event)) {
                return i;
	    }
        }
        return NOT_FOUND;
    }

    /**
     * Increases the capacity of event list by 4
     */
    private void grow() {
        Event[] increasedEvents = new Event[numEvents + CAPACITY_INCREASE];
        for (int i = 0; i < numEvents; ++i) {
            increasedEvents[i] = events[i];
        }
        events = increasedEvents;
    }

    /**
     * Adds an event to the end of the list and grows as needed
     * @param  event the event to be added
     * @return true if event is successfully added
     */
    public boolean add(Event event) {
        if (numEvents == events.length) {
            grow();
        }
        events[numEvents] = event;
        ++numEvents;
        return true;
    }

    /**
     * Deletes the specified event from the list if it exists
     * @param  event the event to be deleted
     * @return true if event is successfully deleted,
     *         false if event is not found in list
     */
    public boolean remove(Event event) {
        if (numEvents == EMPTY) {
            return false;
        }

        int indexOfEvent = find(event);
        if (indexOfEvent == NOT_FOUND) {
            return false;
        }

        for (int i = indexOfEvent; i < numEvents - 1; ++i) {
            events[i] = events[i + 1];
        }

        --numEvents;
        events[numEvents] = null;

        return true;
    }

    /**
     * Checks whether an event exists in the list
     * @param  event the event to be searched
     * @return true if list contains specified event, false if not
     */
    public boolean contains(Event event) {
        for (int i = 0; i < numEvents; ++i) {
            if (events[i].equals(event)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Prints the array of events in its current order
     */
    public void print() {
        if (numEvents == EMPTY) {
            System.out.println("Event calendar is empty!");
            return;
        }
        System.out.println("* Event calendar *");
        printEvents();
        System.out.println("* end of event calendar *");
    }

    /**
     * Prints the array of events ordered by date and timeslot
     */
    public void printByDate() {
        if (numEvents == EMPTY) {
            System.out.println("Event calendar is empty!");
            return;
        }
        for (int i = 1; i < numEvents; ++i) {
            Event key = events[i];
            int j = i - 1;
            while (j >= 0 && events[j].compareTo(key) > 0) {
                events[j + 1] = events[j];
                --j;
            }
            events[j + 1] = key;
        }
        System.out.println("* Event calendar by event date"
                + " and start time *");
        printEvents();
        System.out.println("* end of event calendar *");
    }

    /**
     * Prints the array of events ordered by campus and building/room
     */
    public void printByCampus() {
        if (numEvents == EMPTY) {
            System.out.println("Event calendar is empty!");
            return;
        }
        for (int i = 1; i < numEvents; ++i) {
            Event key = events[i];
            int j = i - 1;
            while (j >= 0 && events[j].getLocation().compareTo(key.getLocation()) > 0) {
                events[j + 1] = events[j];
                --j;
            }
            events[j + 1] = key;
        }
        System.out.println("* Event calendar by campus and building *");
        printEvents();
        System.out.println("* end of event calendar *");
    }

    /**
     * Prints the array of events ordered by department
     */
    public void printByDepartment() {
        if (numEvents == EMPTY) {
            System.out.println("Event calendar is empty!");
            return;
        }
        for (int i = 1; i < numEvents; ++i) {
            Event key = events[i];
            int j = i - 1;
            while (j >= 0 && events[j].getDepartment().compareTo(key.getDepartment()) > 0) {
                events[j + 1] = events[j];
                --j;
            }
            events[j + 1] = key;
        }
        System.out.println("* Event calendar by department *");
        printEvents();
        System.out.println("* end of event calendar *");
    }

    /**
     * Helper method used to print events
     */
    private void printEvents() {
        for (int i = 0; i < numEvents; ++i) {
            System.out.println(events[i]);
        }
    }
}
