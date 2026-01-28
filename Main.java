import java.util.Arrays;

public class Main
{
    public static void main(String[] args)
    {
        NewCipher runner1 = new NewCipher();
        String input = "I'm an amazing coder!";
        String[] encrypted = runner1.encrypt(input);
        System.out.println(encrypted[0]);
        System.out.println(encrypted[1]);
        String decrypt = runner1.decrypt(encrypted[0], encrypted[1]);
        System.out.println(decrypt);
    }
}
