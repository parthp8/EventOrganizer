package eventorganizer;

import java.util.Calendar;

/**
 * Represents a date with year, month, and day
 * @author Jeeva Ramasamy, Parth Patel
 */
public class Date implements Comparable<Date> {
    private int year;
    private int month;
    private int day;

    private static final int GREATER_THAN = 1;
    private static final int LESS_THAN = -1;

    private static final int JANUARY = 1;
    private static final int MARCH = 3;
    private static final int APRIL = 4;
    private static final int MAY = 5;
    private static final int JUNE = 6;
    private static final int JULY = 7;
    private static final int AUGUST = 8;
    private static final int SEPTEMBER = 9;
    private static final int OCTOBER = 10;
    private static final int NOVEMBER = 11;
    private static final int DECEMBER = 12;

    private static final int QUADRENNIAL = 4;
    private static final int CENTENNIAL = 100;
    private static final int QUARTERCENTENNIAL = 400;

    private static final int FIRST_DAY_OF_MONTH = 1;
    private static final int MAX_DAYS = 31;
    private static final int FEBRUARY_DAYS = 28;
    private static final int FEBRUARY_DAYS_LEAP_YEAR = 29;

    private static final int CALENDAR_MONTH_OFFSET = 1;
    private static final int MONTHS_IN_YEAR = 12;
    private static final int MAX_EVENT_MONTH_RANGE = 6;

    /**
     * Creates a Date object with the specified year, month, and day
     * @param year the year in the date of format yyyy
     * @param month the month in the date of format mm
     * @param day the day in the date of format dd
     */
    public Date(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    /**
     * Checks whether the year of the event is a leap year or not
     * @return true if leap year, false otherwise
     */
    private boolean isLeapYear() {
        if (this.year % QUADRENNIAL == 0) {
            if (this.year % CENTENNIAL == 0) {
                return this.year % QUARTERCENTENNIAL == 0;
            }
            else {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the date is a valid calendar date
     * @return true if valid date, false otherwise
     */
    public boolean isValid()  {
        // Checks if the month is valid
        if (this.month < JANUARY || this.month > DECEMBER) {
            return false;
        }

        // Checks if the day is valid
        if (this.day < FIRST_DAY_OF_MONTH) {
            return false;
        }
        else if (this.month == JANUARY || this.month == MARCH
                || this.month == MAY || this.month == JULY
                || this.month == AUGUST || this.month == OCTOBER
                || this.month == DECEMBER) {
            return this.day <= MAX_DAYS;
        }
        else if (this.month == APRIL || this.month == JUNE
                || this.month == SEPTEMBER || this.month == NOVEMBER) {
            return this.day < MAX_DAYS;
        }
        else {
            if (isLeapYear()) {
                if (this.day > FEBRUARY_DAYS_LEAP_YEAR) {
                    return false;
                }
                else {
                    return true;
                }
            }
            else {
                return this.day <= FEBRUARY_DAYS;
            }
        }
    }

    /**
     * Compares this date with the specified date
     * @param  date the object to be compared.
     * @return 1 if this date occurs after the specified date,
     *         -1 if this date occurs before the specified date,
     *         0 if the dates are the same
     */
    @Override
    public int compareTo(Date date) {
        if (this.year > date.year) {
            return GREATER_THAN;
        }
        else if (this.year < date.year) {
            return LESS_THAN;
        }
        else {
            if (this.month > date.month) {
                return GREATER_THAN;
            }
            else if (this.month < date.month) {
                return LESS_THAN;
            }
            else {
                return Integer.compare(this.day, date.day);
            }
        }
    }

    /**
     * Checks whether the event date is in the future
     * @return true if future date, false otherwise
     */
    public boolean isFutureDate() {
        int todayYear = Calendar.getInstance().get(Calendar.YEAR);
        int todayMonth = Calendar.getInstance().get(Calendar.MONTH)
                + CALENDAR_MONTH_OFFSET;
        int todayDay = Calendar.getInstance().get(Calendar.DATE);
        Date today = new Date(todayYear, todayMonth, todayDay);

        // event date is in the future
        return this.compareTo(today) > 0;
    }

    /**
     * Checks whether the event date is within six months of today's date
     * @return true if this date is within 6 months, false otherwise
     */
    public boolean isWithinSixMonths() {
        int todayYear = Calendar.getInstance().get(Calendar.YEAR);
        int todayMonth = Calendar.getInstance().get(Calendar.MONTH)
                + CALENDAR_MONTH_OFFSET;
        int todayDay = Calendar.getInstance().get(Calendar.DATE);

        int maxYear = todayYear, maxDay = todayDay;
        int maxMonth = (todayMonth + MAX_EVENT_MONTH_RANGE);
        if (maxMonth > MONTHS_IN_YEAR) {
            ++maxYear;
            maxMonth -= MONTHS_IN_YEAR;
        }
        Date maxDateForEvent = new Date(maxYear, maxMonth, maxDay);

        // event date is within six months in the future
        return this.compareTo(maxDateForEvent) <= 0;
    }

    /**
     * Returns a string representation of the date
     * @return string version of date in format mm/dd/yyyy
     */
    @Override
    public String toString() {
        return "" + this.month + '/' + this.day + '/' + this.year;
    }

    /**
     * Test case #1
     * Tests if date is in a non-leap year February
     */
    private static void testFebDays_NonLeap() {
        System.out.println("** Test case #1: Only 28 days in "
                + "a non-leap year February");
        Date date = new Date(2021, 2, 29);
        boolean expectedOutput = false, actualOutput = date.isValid();
        testResult(date, expectedOutput, actualOutput);
    }

    /**
     * Test case #2
     * Tests if date is in a leap year February
     */
    private static void testFebDays_Leap() {
        System.out.println("** Test case #2: 29 days in "
                + "a leap year February");
        Date date = new Date(2020, 2, 29);
        boolean expectedOutput = true, actualOutput = date.isValid();
        testResult(date, expectedOutput, actualOutput);
    }

    /**
     * Test case #3
     * Tests if date's month is not before January
     */
    private static void testMonth_BelowRange() {
        System.out.println("** Test case #3: Valid months range is (1-12)");
        Date date = new Date(2023, -1, 4);
        boolean expectedOutput = false, actualOutput = date.isValid();
        testResult(date, expectedOutput, actualOutput);
    }

    /**
     * Test case #4
     * Tests if date's month is not past December
     */
    private static void testMonth_AboveRange() {
        System.out.println("** Test case #4: Valid months range is (1-12)");
        Date date = new Date(2023, 13, 5);
        boolean expectedOutput = false, actualOutput = date.isValid();
        testResult(date, expectedOutput, actualOutput);
    }

    /**
     * Test case #5
     * Tests if the date's day is not before the 1st valid day (1)
     */
    private static void testDays_BelowRange() {
        System.out.println("** Test case #5: The first valid day is (1)");
        Date date = new Date(2023, 2, 0);
        boolean expectedOutput = false, actualOutput = date.isValid();
        testResult(date, expectedOutput, actualOutput);
    }

    /**
     * Test case #6
     * Tests if the date's day is not after the
     * last valid day in January (31)
     */
    private static void testDaysInJan_AboveRange() {
        // Same case for January, March, May, July, August, October, December
        System.out.println("** Test case #6: The last valid day in January "
                + "is (31)");
        Date date = new Date(2022, 1, 32);
        boolean expectedOutput = false, actualOutput = date.isValid();
        testResult(date, expectedOutput, actualOutput);
    }

    /**
     * Test case #7
     * Tests if the date's day is not after the last valid day in April (30)
     */
    private static void testDaysInApr_AboveRange() {
        // Same case for April, June, September, November
        System.out.println("** Test case #7: The last valid day in April"
                + " is (30)");
        Date date = new Date(2021, 4, 31);
        boolean expectedOutput = false, actualOutput = date.isValid();
        testResult(date, expectedOutput, actualOutput);
    }

    /**
     * Test case #8
     * Tests if the date's day is within the range of
     * valid days in June (1-30)
     */
    private static void testDaysInJune_WithinRange() {
        // Same case for April, June, September, November
        System.out.println("** Test case #8: The valid range of days in June"
                + " is (1-30)");
        Date date = new Date(2020, 6, 19);
        boolean expectedOutput = true, actualOutput = date.isValid();
        testResult(date, expectedOutput, actualOutput);
    }

    /**
     * Prints the result from the test case whether it passes or fails
     * @param date the calendar date being tested
     * @param expectedOutput this is what the test case should return
     * @param actualOutput this is what the test case returned
     */
    private static void testResult(Date date, boolean expectedOutput,
                                   boolean actualOutput) {
        System.out.println("Test Input: " + date);
        System.out.println("Expected Input: " + expectedOutput
                + "  vs  " + "Actual Input: " + actualOutput);
        System.out.println((expectedOutput == actualOutput) ?
                "(PASS)" : "(FAIL)");
    }

    /**
     * Testbed main used as the driver to test isValid()
     * @param args command line arguments
     */
    public static void main(String[] args) {
        testFebDays_NonLeap();
        testFebDays_Leap();
        testMonth_BelowRange();
        testMonth_AboveRange();
        testDays_BelowRange();
        testDaysInJan_AboveRange();
        testDaysInApr_AboveRange();
        testDaysInJune_WithinRange();
    }
}