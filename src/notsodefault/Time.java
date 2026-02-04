package notsodefault;

public class Time {
    private int hours;
    private int minutes;
    private int seconds;
    
    private int stand;
    private boolean post = false;
    

    private String UnTime;
    private String StTime;
    
    public Time(int hours, int minutes, int seconds) {
        if (hours <= 23 && minutes <= 59 && seconds <= 59) {
            this.hours = hours;
            this.minutes = minutes;
            this.seconds = seconds;
        }
        else {
            System.out.println("Wrong format, but OK");
            minutes += seconds / 60;
            seconds = seconds % 60;

            hours += minutes / 60;
            minutes = minutes % 60;

            hours = hours % 24;

            this.hours = hours;
            this.minutes = minutes;
            this.seconds = seconds;  
        }
        
        if (hours / 12 == 1) {
                this.stand = hours % 12;
                this.post = true;
            }
    }

    public String toUniversal() {
        UnTime = hours + ":" + minutes + ":" + seconds;
        return UnTime;
    }
    
    public void add(int h, int m, int s) {
    	hours += h;
    	minutes += m;
    	seconds += s;
    	
    	minutes += seconds / 60;
        seconds = seconds % 60;

        hours += minutes / 60;
        minutes = minutes % 60;

        hours = hours % 24;
        
        if (hours / 12 == 1) {
            stand = hours % 12;
            post = true;
        }
        else {
        	stand = hours;
        	post = false;
        }
    }
    
    public String toStandard() {
    	StTime = stand + ":" + minutes + ":" + seconds;
    	if(post == true) {
    		StTime += " PM";
    	}
    	else {
    		StTime += " AM";
    	}
    	return StTime;
    }

}