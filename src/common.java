import java.text.SimpleDateFormat;
import java.util.Date;

public class common {
    /**
     * This method is to get datetime with format dd/MM/yyyy HH:mm:ss
     * https://www.javatpoint.com/java-get-current-date
     * @return
     */
    public static String getDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return formatter.format(date);
    }
}
