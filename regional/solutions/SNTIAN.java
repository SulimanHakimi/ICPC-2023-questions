package com.company;
//Coded By Ali Akbar Ahmadi, September 8 2019, 20:00 PM
//To run the code outside the intelliJ please comment the package.
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class SNTIAN {

    public static void main(String[] args) throws IOException {
        BufferedReader comment_reader = new BufferedReader( new InputStreamReader( System.in ) );
        int test_case = Integer.parseInt( comment_reader.readLine() );
        int [] positive_result = new int[test_case];
        int [] negative_result = new int[test_case];
        String [] positives = {"good", "nice", "like", "mashallah", "barakallah", "tashakor", "khobish", "popular"};
        List<String> list_positives = Arrays.asList(positives);
        String [] negatives = {"bad", "zesht", "lier", "manfi", "impossible", "mariz", "poor", "hunger"};
        List<String> list_negatives = Arrays.asList(negatives);
        for (int j = 0; j < test_case; j++)
        {
            int  positive = 0;
            int  negative = 0;
            int expected_comments = Integer.parseInt( comment_reader.readLine() );
            for (int z = 0; z < expected_comments; z++){
                boolean test_positive = false;
                String [] comments_words = comment_reader.readLine().toLowerCase().replaceAll("\\.", "").split( " " );
                for (String word : comments_words){
                    if (list_positives.contains(word)){
                        test_positive = true;
                        break;
                    }
                }
                if (test_positive) {
                    positive +=1;
                }
                else
                    negative +=1;
            }
            positive_result[j] = positive;
            negative_result[j] = negative;
        }
        for (int i = 0; i < test_case; i++){
            System.out.println("Positive: " + Integer.toString(positive_result[i]) + " " + "Negative: " + Integer.toString(negative_result[i]));
        }
        comment_reader.close();
    }
}
