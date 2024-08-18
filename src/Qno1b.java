/*Imagine you have a secret decoder ring with rotating discs labeled with the lowercase alphabet. You're given a
        #message s written in lowercase letters and a set of instructions shifts encoded as tuples (start_disc, end_disc,
#direction). Each instruction represents rotating the discs between positions start_disc and end_disc (inclusive)
#either clockwise (direction = 1) or counter-clockwise (direction = 0). Rotating a disc shifts the message by one
#letter for each position moved on the alphabet (wrapping around from ‘z’ to ‘a’ and vice versa).
        #Your task is to decipher the message s by applying the rotations specified in shifts in the correct order*/

public class Qno1b {

    public static void main(String[] args) {
        String s = "hello";
        int[][] shifts = {
                {0, 1, 1},  // Rotate discs 0 and 1 clockwise
                {2, 3, 0},  // Rotate discs 2 and 3 counter-clockwise
                {0, 2, 1}   // Rotate discs 0, 1, and 2 clockwise
        };

        String output = secretDecoder(s, shifts);
        System.out.println(output);  // Output: "jglko"
    }

    // Method to rotate a single character
    public static char rotateCharacter(char ch, int direction) {
        if (direction == 1) {  // Clockwise rotation
            return (char) ((ch - 'a' + 1) % 26 + 'a');
        } else {  // Counter-clockwise rotation
            return (char) ((ch - 'a' - 1 + 26) % 26 + 'a');
        }
    }

    // Method to decode the message
    public static String secretDecoder(String s, int[][] shifts) {
        // Convert the string to a char array for easier manipulation
        char[] chars = s.toCharArray();

        // Apply each shift in the shifts array
        for (int[] shift : shifts) {
            int start = shift[0];
            int end = shift[1];
            int direction = shift[2];

            for (int i = start; i <= end; i++) {
                chars[i] = rotateCharacter(chars[i], direction);
            }
        }

        // Convert the char array back to a string and return it
        return new String(chars);
    }
}
