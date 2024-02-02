package ro.ase.ism.hardwaresec;

import java.io.*;

public class Caesar {
    //Mihalache Andrei
    //Caesar cypher solution implemented in java 8

    //Will use english alphabet
    //A B C D E F G H I J K  L  M  N  O  P  Q  R  S  T  U  V  W  X  Y  Z
    //0 1 2 3 4 5 6 7 8 9 10 11 13 14 15 16 17 18 19 20 21 22 23 24 25 26

    public static String encrypt (String content, int key)
    {
        StringBuilder builder = new StringBuilder();
        for (char character : content.toCharArray())
        {
            if(Character.isLetter(character)) {
                int idx = Character.isLowerCase(character) ? 97 : 65;
                character = (char) ((character + key - idx) % 26 + idx);
            }
            builder.append(character);
        }
        return builder.toString();
    }

    public static String decrypt (String content, int key)
    {
        return encrypt(content, 26 - (key % 26));
    }

    public static String readFile(File file) throws IOException {
        FileReader fr = new FileReader(file);
        try (BufferedReader br = new BufferedReader(fr)) {
            StringBuilder builder = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                builder.append(line);
                line = br.readLine();
            }
            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void writeFile(File file, String content) throws IOException {
        FileWriter fw = new FileWriter(file);
        try (BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        if(args.length != 3)
        {
            System.out.println("Arguments should be mode (encrypt/decrypt), inputfile (text file name), key (string)");
        }
        else{
            boolean encryptMode;
            File file = null;
            int key;

            try{

                if(args[0].equals("encrypt"))
                {
                    encryptMode = true;
                }
                else if(args[0].equals("decrypt"))
                {
                    encryptMode = false;
                }
                else throw new IllegalArgumentException("First argument should be encryption mode - encrypt / decrypt");


                file = new File(args[1]);
                if (!file.exists())
                {
                   throw new IOException("File does not exists");
                }

                key = Integer.parseInt(args[2]);

                String content = readFile(file);

                if (encryptMode) {
                    writeFile(new File("enc" + args[1]), encrypt(content, key));
                } else {
                    writeFile(new File("dec" + args[1]), decrypt(content, key));
                }

            }
            catch (NumberFormatException e)
            {
                System.out.println("Enter a valid number as a key");
            }
            catch (IllegalArgumentException | IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
