import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class KABISA {

    public static void main(String[] args) throws IOException {
        BufferedReader kabisa_reader = new BufferedReader( new InputStreamReader( System.in ) );
        int test_case = Integer.parseInt(kabisa_reader.readLine());
        ArrayList<String> leap_years = new ArrayList<>();
        for (int j = 0; j < test_case; j++) {
            String given_years = kabisa_reader.readLine();
            String[] given_years_in_array = given_years.split( ", " );
            String leap_year = "";
            for (int i = 0; i <given_years_in_array.length; i++){
                if (Integer.parseInt(given_years_in_array[i])%4 == 0){
                    leap_year += given_years_in_array[i] + " ";
                }
            }
            leap_years.add(leap_year);
        }
        for (String leap: leap_years){
            System.out.printf(leap);
            System.out.println();
        }

    }
}
