// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP112 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP112 Assignment 1
 * Name:
 * Usercode:
 * ID:
 */

import ecs100.*;
import java.awt.Color;


/**
 * Checks dates, prints out in long format and draws calendars.
 * The processDates method
 * Reads a date from the user as three integers, and then
 * (a) checks that the date is valid (ie, represents a real date,
 *     taking into account leap years), reporting if it is not valid.
 *     (It may assume the standard modern calendar, and isn't required
 *      to give correct answers for dates before the 1600's when the
 *      calendar was different.
 * (b) If the date is valid, it prints out the date in a long form: eg
 *     Monday 3rd March, 2014. This requires working out which day
 *     of the week the date is.
 *     (Core only needs dates this year)
 * (c) It draws a one week calendar, highlighting the date:
 *     It shows the seven days of the week as a row of rectangles, highlighting
 *      the day corresponding to the date. It doesn't need to show the dates
 *      for each day.
 * (d) (Completion) It draws a monthly calendar for the month containing the date:
 *     It should draw a title containing the month and the year, and a
 *     grid of rectangles for each day of the month, giving the day of the month
 *     in each rectangle. This will be between 4 rows and 6 rows of 7 rectangles.
 *     The ISO standard for calendars specifies that the first day of each week
 *     should be a Monday.
 *     Ideally, the calendar should include the last few days of the previous
 *     month when the month doesn't start on a Monday and the first few days
 *     of the next month when the month doesn't end on a Sunday.
 *
 * Reasonable design would have a number of methods, for example:
 *  isValidDate  which would return a boolean (true or false)
 *  isLeapYear   which would return a boolean (true or false)
 *  findDay      which would return the day of the week as an int (0 to 6)
 *  drawWeek     which would draw the weekly "calendar"
 *  drawMonth    which would draw the monthly calendar
 * You might choose to design it differently, but doing it all in one huge method
 *  would not be good design.
 */

public class DateChecker {
	
	public int[] dateInfo = new int[3];

    public int[] monthLengths = new int[] {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    public String[] monthNames = new String[] { "January", "February", "March", "April", "May",
    		"June", "July", "August", "September", "October", "November", "December"};
    public String[] dayNames = new String[] {"monday", "tuesday", "wednesday", "thursday", 
    		"friday", "saturday", "sunday"};
    
    public void processDates(){
        while(true)  {
            if(!processADate()){
            	UI.print("that was not a valid date.\n");
            	UI.sleep(1000);
            }
            else{
            	DrawCalender();
            }
        }
    }


    public boolean processADate(){
    	String userDate = UI.askString("Pleae enter a date: (in the format: d/m/y)");
    	String[] splitString = userDate.split("/");
    	
    	if(splitString.length != 3)
    		return(false);
    	
    	for(int i = 0; i < 3; i ++){
    		try {
    		      dateInfo[i] = Integer.parseInt(splitString[i]);
    		} 
    		catch (NumberFormatException e) {
    			return(false);
    		}
    	}
    	
    	if(dateInfo[1] > 12 || dateInfo[1] < 1)
    		return(false);
    	
    	if(dateInfo[1] == 2 && CheckForLeapYear(dateInfo[2])){
    		if(dateInfo[0] > 29)
    			return(false);
    	}
    	else if(dateInfo[0] > monthLengths[dateInfo[1]-1] || dateInfo[0] < 1)
    		return(false);
    	
    	if(String.valueOf(dateInfo[2]).length() != 4)
    		return(false);
    	
    	UI.print(dayNames[GetDay(dateInfo)] + ", " + monthNames[dateInfo[1]-1] + " " + dateInfo[0] + ", " + dateInfo[2] + "\n");
    	if(CheckForLeapYear(dateInfo[2]))
    		UI.print("note: " + dateInfo[2] + " is a leap year.\n");
    	return(true);
    }
    
    boolean CheckForLeapYear(int year){
    	if((year % 8 == 0) || (year % 100 == 0) || (year % 4 == 0))
    		return (true);
    	else 
    		return (false);
    }
    
    public void DrawCalender(){
    	
    	 UI.clearGraphics();
    	 
    	 int currentMonthStartingDay = GetDay(new int[] {1, dateInfo[1], dateInfo[2]});
    	 int monthCounter = 0;
    	 boolean drawMonth = false;
    	 
    	 UI.setColor(new Color (100, 100, 100));
    	 UI.setFontSize(14);
    	 UI.drawString("" + monthNames[dateInfo[1]-1] + ", " + dateInfo[2], 220, 45);
    	 
    	 UI.setFontSize(10);
    	 
    	 for(int y = 0; y < 6; y ++){
	    	 for(int x = 0; x < 7; x ++){
	    		 UI.setColor(new Color (240, 240, 240));
	    		 UI.fillRect(50 + 60 * x, 80 + 40 * y, 58, 38);
	    		 
	    		 if(y == 0){
	    			 UI.setColor(new Color (140, 140, 140));
	    			 UI.drawString(dayNames[x], 50 + 60 * x, 70);
	    			 
	    			 if(x == currentMonthStartingDay)
	    				 drawMonth = true;
	    		 }
	    		 
	    		 if(drawMonth){
	    			 monthCounter ++;
	    			 if(dateInfo[1] == 2 && CheckForLeapYear(dateInfo[2])){
    		    		if(monthCounter > 29){
    		    			drawMonth = false;
	    				 	continue;
    		    		}
	    		     }
	    			 else if(monthCounter > monthLengths[dateInfo[1]-1]){
	    				 drawMonth = false;
	    				 continue;
	    			 }
	    			 
	    			 UI.setColor(new Color (200, 200, 200));
		    		 UI.fillRect(50 + 60 * x, 80 + 40 * y, 58, 38);
		    		 
		    		 if(monthCounter == dateInfo[0]){
		    			 UI.setColor(new Color (225, 255, 205));
			    		 UI.fillRect(50 + 60 * x, 80 + 40 * y, 58, 38);
		    		 }
		    		 
		    		 UI.setColor(new Color (100, 100, 100));
	    			 UI.drawString("" + monthCounter, 52 + 60 * x, 90 + 40 * y);
	    		 }
	    	 }
    	 }
    }
    
    public int GetDay(int[] date){
    	
    	// note: this segment of code is based off (but changed for use in java) a formula found on this page:
    	// http://faculty.cs.niu.edu/~hutchins/csci230/zeller.htm
    	
    	int newYear = date[2];
    	int A = date[1];
    	
    	if (A <= 2){
    		A += 10; //a
    		newYear -= 1;
    	}
    	else 
    		A -= 2;

    	int C = newYear % 100; //c
    	int D = newYear / 100; //d

        int B = date[0];
        int W = (13 * A - 1) / 5;	
        int X = C / 4;
        int Y = D / 4;
        int Z = W + X + Y + B + C - 2 * D;
        int R = Z % 7;
	
	    if(R < 0)
	        R += 7;
	    
	    if(R == 0)
	    	R = 6;
	    else
	    	R --;
	    
    	return (R);

    }


    public static void main(String[] arguments){
    	UI.initialise();
        DateChecker dc = new DateChecker();
        dc. dateInfo = new int[] {1,1,2015};
        dc.DrawCalender();
        dc.processDates();
    }        

}
