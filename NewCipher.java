import java.util.ArrayList;
import java.util.Arrays;

public class NewCipher {
    private final String[] letterToName = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen", "twenty", "twentone",  "twenttwo", "twentthree", "twentfour", "twentfive", "twentsix"};
    private final String[] converts = {"aa", "ab", "ba", "bb"};
    private final String alphabet = "abcdefghijklmnopqrstuvwxyz";
    private final String[] alphabetList;
    public NewCipher(){
        alphabetList = alphabet.split("");
    }

    public String standardize(String input){
        String output = "";
        input = input.toLowerCase();
        for (int i = 0; i < input.length(); i++) {
            String character = input.substring(i, i + 1);
            int letter = alphabet.indexOf(character);
            if (letter != -1){
                output += character;
            }
        }
        return output;
    }

    public int[] numberInput(String input){
        int[] numberedInput = new int[input.length()];
        for (int i = 0; i < input.length(); i++){
            String character = input.substring(i, i + 1);
            numberedInput[i] = alphabet.indexOf(character);
        }
        return numberedInput;
    }

    public String numbersToNames(int[] numbers){
        String output = "";
        for (int number : numbers){
            output += letterToName[number];
        }
        return output;
    }

    public String baconify(String input) {
        String output = "";
        for (int i = 0; i < input.length(); i++) {
            String character = input.substring(i, i + 1);
            int letter = alphabet.indexOf(character);
            String outletter = "";
            int counter = 4;
            while (counter >= 0) {
                int base = (int) Math.pow(2, counter);
                if (letter >= base) {
                    outletter += "b";
                    letter -= base;
                } else {
                    outletter += "a";
                }
                counter--;
            }
            output += outletter;
        }
        return output;
    }

    public int[][] chartMaker(){
        int[][] output = new int[2][4];
        ArrayList<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8));
        for (int i = 0; i < 8; i++){
            int instance = (int)(Math.random() * numbers.size());
            output[(int)(i / 4)][i % 4] = numbers.get(instance);
            numbers.remove(instance);
        }
        return output;
    }

    public String baconEvener(String bacon){
        if (bacon.length() % 2 == 1){
            bacon += "a";
        }
        return bacon;
    }

    public String baconToString(String bacon, int[][] key){
        String output = "";
        bacon = baconEvener(bacon);
        for (int i = 0; i < bacon.length() / 2; i++){
            String pair = bacon.substring(i * 2, (i + 1) * 2);
            output += key[(int)(Math.random() * 2)][keyFinder(pair)];
        }
        return output;
    }

    public int keyFinder(String input){
        for (int i = 0; i < 4; i++){
            if (converts[i].equals(input)){
                return i;
            }
        }
        return -1;
    }

    public String keyToPrint(int[][] key){
        String output = "";
        for (int[] row : key){
            for (int i : row){
                output += i;
            }
        }
        return output;
    }

    public String[] encrypt(String input) {
        input = standardize(input);
        int[] numberedList = numberInput(input);
        input = numbersToNames(numberedList);
        input = baconify(input);
        int[][] key = chartMaker();
        input = baconToString(input, key);
        String printedKey = keyToPrint(key);
        String[] output = {input, printedKey};
        return output;
    }

    public int[][] printedToKey(String key){
        int[][] output = new int[2][4];
        for (int i = 0; i < key.length(); i++){
            output[i / 4][i % 4] = Integer.parseInt(key.substring(i, i + 1));
        }
        return output;
    }

    public int whereInKey(int number, int[][] trueKey){
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 2; j++){
                if (trueKey[j][i] == number){
                    return i;
                }
            }
        }
        return -1;
    }

    public String outToBacon(String input, String key){
        String output = "";
        int[][] trueKey = printedToKey(key);
        for (int i = 0; i < input.length(); i++){
            int number = Integer.parseInt(input.substring(i, i + 1));
            output += converts[whereInKey(number, trueKey)];
        }
        return output;
    }

    public String standardBacon(String input){
        if (input.length() % 5 != 0){
            input = input.substring(0, input.length() - 1);
        }
        return input;
    }

    public String baconToWords(String input){
        String output = "";
        input = standardBacon(input);
        for (int i = 0; i < (input.length() / 5); i++){
            output += baconLetter(input.substring(i * 5, (i + 1) * 5));
        }
        return output;
    }

    public String baconLetter(String input){
        int letter = 0;
        int counter = 16;
        for (int i = 0; i < input.length(); i++){
            String character = input.substring(i, i + 1);
            if (character.equals("b")){
                letter += counter;
            }
            counter /= 2;
        }
        return alphabet.substring(letter, letter + 1);
    }

    public String wordsToFinal(String input){
        ArrayList<String> words = new ArrayList<>();
        ArrayList<Integer> indexes = new ArrayList<>();
        while (!(input.equals(input.toUpperCase()))) {
            for (int i = letterToName.length - 1; i >= 0; i--){
                String word = letterToName[i];
                while (input.indexOf(word) >= 0){
                    int index = input.indexOf(word);
                    words.add(word);
                    indexes.add(index);
                    input = input.substring(0, index) + word.toUpperCase() + input.substring(index + word.length());
                }
            }
        }
        ArrayList<String> chars = new ArrayList<>();
        for (int i = 0; i < words.size(); i++){
            String word = words.get(i);
            for (int j = 0; j < letterToName.length; j++){
                if (letterToName[j].equals(word)){
                    chars.add(i, alphabet.substring(j, j + 1));
                }
            }
        }
        //right now i have a list of indexes, and a corresponding list of characters where in the final message,
        //the index of the char matches with the final index
        String output = "";
        int originalSize = indexes.size();
        for (int i = 0; i < originalSize; i++){
            int min = 0;
            for (int j = 0; j < indexes.size(); j++) {
                if (indexes.get(j) < indexes.get(min)) {
                    min = j;
                }
            }
            indexes.remove(min);
            output += chars.get(min);
            chars.remove(min);
        }
        return output;
    }

    public String decrypt(String input, String key){
        input = outToBacon(input, key);
        input = baconToWords(input);
        input = wordsToFinal(input);
        return input;
    }
}
