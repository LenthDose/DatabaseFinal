package Tool;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTime {
    public static String getDate(){
        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return dateFormat.format(date);
    }
}
