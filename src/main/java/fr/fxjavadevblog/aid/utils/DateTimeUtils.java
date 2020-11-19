package fr.fxjavadevblog.aid.utils;

import java.time.Duration;

public final class DateTimeUtils {

    private DateTimeUtils() {
        // protecting 
    }
	
	public static String format(Duration d) {
	    long days = d.toDays();
	    Duration d1 = d.minusDays(days);
	    long hours = d1.toHours();
	    Duration d2 = d1.minusHours(hours);
	    long minutes = d2.toMinutes();
	    Duration d3 = d2.minusMinutes(minutes);
	    long seconds = d3.getSeconds() ;
	    return 
	            (days ==  0 ? "" : days  +"D ")+ 
	            (hours == 0?"":hours+" h ")+ 
	            (minutes ==  0?"":minutes+" m ")+ 
	            (seconds == 0?"":seconds+" s");
	}

}
