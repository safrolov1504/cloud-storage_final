package workingWithMessage;

import workWithSQL.BaseSQLServer;

import java.sql.SQLException;
import java.util.Arrays;

public class SignIn {

    public static boolean checkUser(byte[] in){

        byte userLength = in[0];

        byte userByte [] = Arrays.copyOfRange(in,1,userLength+1);
        byte passByte [] = Arrays.copyOfRange(in, userLength+2,in.length);

        System.out.println("From auth");
        System.out.println(Arrays.toString(in));
        System.out.println(Arrays.toString(userByte));
        System.out.println(Arrays.toString(passByte));

        String user = new String(userByte);
        String pass = new String(passByte);

        System.out.println(user+" "+pass);

        return sendToQSL(user, pass);
    }

    private static boolean sendToQSL(String user, String pass) {
        BaseSQLServer sqlServer = new BaseSQLServer();

        boolean flag = false;
        try {
            sqlServer.start();
            flag = sqlServer.checkUser(user,pass);
            System.out.println(flag);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                sqlServer.stop();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
       return flag;
    }

}
