package ics.ci.mfausermanagmentjava.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateConvert {
    public static String getStringDate(LocalDateTime dateTime){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return dateTime.format(formatter);
    }

    public static String getStringDate(Date dateTime){

       // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(dateTime);
    }
}
