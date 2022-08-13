package bullscows;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        grade();
    }

    static String getSecretCode() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please, enter the secret code's length:");
        String codeLenStr = scanner.next();
        int codeLength;
        try {
            codeLength = Integer.parseInt(codeLenStr);
        } catch (NumberFormatException e) {
            System.out.println("Error: \"" + codeLenStr + "\" isn't a valid number.");
            return null;
        }

        System.out.println("Input the number of possible symbols in the code:");
        String symLenStr = scanner.next();
        int symLength;
        try {
            symLength = Integer.parseInt(symLenStr);
        } catch (NumberFormatException e) {
            System.out.println("Error: \"" + symLenStr + "\" isn't a valid number.");
            return null;
        }

        if (symLength > 36) {
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            return null;
        } else if (codeLength > symLength || codeLength == 0) {
            System.out.println("Error: it's not possible to generate a code with a length of " + codeLength + " with " + symLength + " unique symbols.");
            return null;
        }

        StringBuilder code = new StringBuilder();
        StringBuilder ban = new StringBuilder();
        Random random = new Random();
        StringBuilder hidden = new StringBuilder();
        char charRange = (char) (96 + (symLength - 10));

        for (int i = 0; i < codeLength; i++) {
            int current = random.nextInt(10);

            if (symLength > 10) {
                symLength -= 10;
                char currentChar = (char) ((random.nextInt(symLength) + 96));
                if (i % 2 == 0 && i != 0) {
                    if (!(ban.toString().contains(currentChar + ""))) {
                        code.append(currentChar);
                        ban.append(currentChar);
                        hidden.append("*");
                    } else {
                        i--;
                    }
                    continue;
                }
            }

            if (!(ban.toString().contains(current + ""))) {
                code.append(current);
                ban.append(current);
                hidden.append("*");
            } else if (code.toString().startsWith("0")) {
                code.delete(0, codeLength);
                ban.delete(0, codeLength);
                i = -1;
            } else {
                i--;
            }

        }

        if (symLength == 0) {
            System.out.println("The secret is prepared: " + hidden + " (0-9).");
        } else if (symLength == 1) {
            System.out.println("The secret is prepared: " + hidden + " (0-9, " + "a" + ").");
        } else {
            System.out.println("The secret is prepared: " + hidden + " (0-9, " + "a-" + charRange + ").");
        }

        return code.toString();
    }

    static void grade() {
        Scanner scanner = new Scanner(System.in);
        String secret = getSecretCode();
        if (null == secret) {
            return;
        }
        System.out.println("Okay, let's start a game!");

        int turn = 1;
        int bulls = 0;
        int cows = 0;

        boolean correct = false;
        while (!correct) {
            System.out.println("Turn " + turn + ":");
            String guess = scanner.next();
            for (int i = 0; i < secret.length(); i++) {
                if (guess.charAt(i) == secret.charAt(i)) {
                    bulls++;
                } else if (secret.contains(guess.charAt(i) + "")){
                    cows++;
                }
            }

            if (bulls > 0 && cows > 0) {
                System.out.println("Grade: " + bulls + " bull(s) and " + cows + "cow(s).");
                bulls = 0;
                cows = 0;
            } else if (bulls > 0) {
                System.out.println("Grade: " + bulls + " bull(s).");
                if (bulls == secret.length()) {
                    correct = true;
                    System.out.println("Congratulations! You guessed the secret code.");
                }
                bulls = 0;
            } else if (cows > 0) {
                System.out.println("Grade: " + cows + " cow(s).");
                cows = 0;
            } else {
                System.out.println("Grade: None");
            }
            turn++;
        }
    }
}
