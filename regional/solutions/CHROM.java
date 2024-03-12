import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CHROM {

    public static void main(String[] args) throws IOException {
        BufferedReader chromosome_reader = new BufferedReader( new InputStreamReader( System.in ) );
        int test_case = Integer.parseInt( chromosome_reader.readLine() );
        ArrayList<String[]> offsprings = new ArrayList<>(test_case);
        for (int j = 0; j < test_case; j++) {
            String [] crossover_points = chromosome_reader.readLine().split(" ");
            String [] parent1 = chromosome_reader.readLine().split(" ");
            String [] parent2 = chromosome_reader.readLine().split(" ");
            String offspring [] = new String[parent1.length];
            String[] gens_parent1 = new String[Integer.parseInt(crossover_points[1])- Integer.parseInt(crossover_points[0])];
            int track_index = Integer.parseInt(crossover_points[1]);

            for (int i=Integer.parseInt(crossover_points[0]); i<Integer.parseInt(crossover_points[1]) ; i++){
                offspring[i] = parent1[i];
                gens_parent1[i-Integer.parseInt(crossover_points[0])] = parent1[i];
//                System.out.printf("%s ",i-Integer.parseInt(crossover_points[0]));
            }
//            System.out.println("End of duplicates");
            for (int r= 0; r <parent1.length; r++){
                int index = r + Integer.parseInt(crossover_points[1]);
                index = index%parent1.length;
                String gen_to_check = parent2[index];
                boolean duplicate_gen = true;
                for (int g=0; g<gens_parent1.length; g++){
                    if (gens_parent1[g].equals(gen_to_check)){
                        duplicate_gen = false;
                        break;
                    }
                }
                if (duplicate_gen){
                    track_index %=parent1.length;
                    offspring[track_index] = gen_to_check;
                    track_index +=1;
                }
            }
            offsprings.add(offspring);
        }
        for(int j = 0; j<test_case; j++){
            String [] offspring_display = offsprings.get(j);
            for (int k = 0; k<offspring_display.length; k++){
                System.out.printf("%s ", offspring_display[k]);
            }
            System.out.println();
        }
    }
}
