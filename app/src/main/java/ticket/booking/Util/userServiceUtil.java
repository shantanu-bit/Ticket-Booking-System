package ticket.booking.Util;
import org.mindrot.jbcrypt.BCrypt;

public class userServiceUtil {
    public static String hashPassword(String plainPassword){
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    public static  boolean checkPass(String plainPassword, String hashedPassword){
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
    public static String generateTicketId(){
        return ""+System.currentTimeMillis();
    }
}
