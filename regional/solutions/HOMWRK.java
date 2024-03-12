package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class HOMWRK {

    public static void main(String[] args) throws IOException {
        BufferedReader game_reader = new BufferedReader( new InputStreamReader( System.in ) );
        int test_case = Integer.parseInt( game_reader.readLine() );
        ArrayList<int[]> results = new ArrayList<>();

        for (int j = 0; j < test_case; j++)
        {
            int number_of_games = Integer.parseInt(game_reader.readLine());
            int [] add = new int[number_of_games];
            int [] mult = new int[number_of_games];
            for (int i = 0; i < number_of_games; i++){

                String [] numbers = game_reader.readLine().split( " " );
                add[i] = Integer.parseInt(numbers[0]) + Integer.parseInt(numbers[1]);
                mult[i] = Integer.parseInt(numbers[0]) * Integer.parseInt(numbers[1]);

            }
            results.add(add);
            results.add(mult);
        }
        for (int z = 0; z < test_case; z++){
            for (int jj = 0; jj < results.get(z+z).length; jj++){
                System.out.println(Integer.toString(results.get(z+z)[jj]) + ' ' + Integer.toString(results.get(z+z+1)[jj]));
            }

        }
        game_reader.close();

    }
}
