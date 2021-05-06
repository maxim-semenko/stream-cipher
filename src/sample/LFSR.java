package sample;

import java.util.Arrays;
import java.util.Collections;

public class LFSR {

    //x32 + x28 + x27 + x + 1
    //11111111111111111111111111111111
    //00000000000000000000000000000000

    public StringBuilder getKey(int size, String input) {
        StringBuilder key = new StringBuilder();
        Integer[] array = new Integer[input.length()];

        for (int i = 0; i < input.length(); i++) {
            array[i] = Integer.parseInt(String.valueOf(input.charAt(i)));
        }

        for (int i = 0; i < size; i++) {
            int result = array[0] ^ array[4] ^ array[5] ^ array[31];
            Collections.rotate(Arrays.asList(array), -1);
            key.append(array[array.length - 1]);
            array[array.length - 1] = result;
        }
        return key;
    }
}
