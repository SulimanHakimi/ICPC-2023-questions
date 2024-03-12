import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class DISPER {
    static float find_mean(String [] input, int max_len){
        float sum = 0;
        for (String mark : input){
            int int_mark = Integer.parseInt( mark );
            if (int_mark>100)
                int_mark = 100;
            sum += int_mark;
        }
        return sum/max_len;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader dispersion_reader = new BufferedReader( new InputStreamReader( System.in ) );
        int test_case = Integer.parseInt( dispersion_reader.readLine() );
        int [] th_professor = new int[test_case];
        float [] th_dispersion = new float[test_case];
        for (int j = 0; j < test_case; j++)
        {
            int professors = Integer.parseInt( dispersion_reader.readLine());
            ArrayList<String[]> all_prof_marks = new ArrayList<>();
            String marks;
            float high_var = -1;
            int pth = 0;
            int max_length = 0;
            for (int i = 0; i < professors; i++) {
                marks = dispersion_reader.readLine();
                String[] marks_in_array = marks.split( ", " );
                if (marks_in_array.length>max_length)
                    max_length = marks_in_array.length;
                all_prof_marks.add(marks_in_array);
            }
            for (int z = 0; z < professors; z++){
                float mean = find_mean( all_prof_marks.get(z), max_length);
                float var = 0;
                for (String mark : all_prof_marks.get(z)) {
                    int new_mark = Integer.parseInt( mark );
                    if (new_mark>100)
                        new_mark = 100;
                    float mark_sub =  new_mark - mean;
                    var += Math.pow( mark_sub, 2 );
                }
                var = var / max_length;
                if (var > high_var) {
                    high_var = var;
                    pth = z+1;
                }
            }
            th_professor[j] = pth;
            th_dispersion[j] = high_var;

        }
        for (int i = 0; i < test_case; i++){
            String high_dip = String.format("%.2f", th_dispersion[i]);
            System.out.println(th_professor[i] + "\t" + high_dip);
        }

        dispersion_reader.close();
    }
}
