import util.DBConnection;

import java.sql.Connection;

public class TestConnection {

    public static void main(String[] args) {

        try {

            Connection conn =
                    DBConnection.getConnection();

            if(conn != null){

                System.out.println(
                        "Connected Success");
            }

        } catch(Exception e){

            e.printStackTrace();
        }
    }

}
