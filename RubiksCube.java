 /*
 * This class contains the functions that do parity side and corner checks, as 
 * well as permutation checks.
 */

package rubik;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Chris
 */
public class RubiksCube {

    private static final int RED = 1;
    private static final int GREEN = 2;
    private static final int YELLOW = 3;
    private static final int BLUE = 4;
    private static final int ORANGE = 5;
    private static final int WHITE = 6;
    
    protected static List<Byte> cube = new ArrayList<>(); 
    
    /**
     * Reads a file containing a 2D representation of a Rubik's Cube and inputs
     * the values into a byte array. 
     * @param filename File containing a 2D representation of a Rubik's Cube
     * @return true if no unexpected characters found and byte array size is 54,
     * false otherwise
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public static boolean readFile(String filename) throws FileNotFoundException, IOException {

        FileReader fr = new FileReader(filename);
        BufferedReader br = new BufferedReader(fr);
        String line = br.readLine();
        boolean check = true;

        while (line != null) {
            line = line.trim();
            char[] c = line.toCharArray();
            for (int i = 0; i < c.length; i++) {
                switch (c[i]) {
                    case 'R':
                        cube.add((byte)RED);
                        break;
                    case 'G':
                        cube.add((byte)GREEN);
                        break;
                    case 'Y':
                        cube.add((byte)YELLOW);
                        break;
                    case 'B':
                        cube.add((byte)BLUE);
                        break;
                    case 'O':
                        cube.add((byte)ORANGE);
                        break;
                    case 'W':
                        cube.add((byte)WHITE);
                        break;
                    default:
                        check = false;
                        System.err.println("Unexpected character found - "
                                + "RubiksCube.readFile function");
                        break;
                }
            }
            line = br.readLine();
        }
        
        return check && cube.size() == 54;
        
    }
    
    /**
     * Cube byte array getter method for testing purposes.
     * @return Cube byte array
     */
    public static List<Byte> getCube() {
        return cube;
    }
    
    /**
     * Checks if the cube contains the proper number (9) of color squares on 
     * each side of the visible cubies.
     * @return true if color count is accurate, false otherwise
     */
    public static boolean colorTest() {
        
        int rCount = 0, gCount = 0, yCount = 0, bCount = 0, oCount = 0, wCount = 0;
        for (int i = 0; i < cube.size(); i++) {
            switch (cube.get(i)) {
                case RED:
                    rCount++;
                    break;
                case GREEN:
                    gCount++;
                    break;
                case YELLOW:
                    yCount++;
                    break;
                case BLUE:
                    bCount++;
                    break;
                case ORANGE:
                    oCount++;
                    break;
                case WHITE:
                    wCount++;
                    break;
                default: //This condition should never be met, see readFile
                    System.err.println("Unexpected byte value found - "
                            + "RubiksCube.colorTest function");
                    break;
            }
        }
        
        return (rCount == 9 && gCount == 9 && yCount == 9 && 
                bCount == 9 && oCount == 9 && wCount == 9);
        
    }
    
    /**
     * Checks if the cube holds accurate middle cubie values (these do not 
     * change regardless of orientation)
     * @return true if middle cubies are accurate, false otherwise
     */
    public static boolean middleTest() {
        
        return cube.get(4) == RED && cube.get(19) == GREEN && cube.get(22) == YELLOW &&
                cube.get(25) == BLUE && cube.get(40) == ORANGE && cube.get(49) == WHITE;
        
    }
    
    /**
     * Checks if the corner cubies exist by running the intermediate test on 
     * every corner based on array location.
     * @return true if corner cubies exist, false otherwise
     */
    public static boolean cornerExistTest() {
        
        int sum = 0;
        
        sum += cornerExistITest(0, 9, 51);
        sum += cornerExistITest(2, 17, 53);
        sum += cornerExistITest(6, 11, 12);
        sum += cornerExistITest(8, 14, 15);
        sum += cornerExistITest(27, 42, 45);
        sum += cornerExistITest(35, 44, 47);
        sum += cornerExistITest(29, 30, 36);
        sum += cornerExistITest(32, 33, 38);
        
        return sum == 36;
    }
    
    /**
     * Intermediate corner existence test. Return values:
     * Red-Green-White = 1, Red-Blue-White = 2, Red-Green-Yellow = 3,
     * Red-Blue-Yellow = 4, Orange-Green-White = 5, Orange-Blue-White = 6,
     * Orange-Green-Yellow = 7, Orange-Blue-Yellow = 8, Total = 36
     * @param c1 first array location of corner
     * @param c2 second array location of corner
     * @param c3 third array location of corner
     * @return 1 to 8 based on which corner is detected, 0 if corner does not exist
     */
    public static int cornerExistITest(int c1, int c2, int c3) {
        if (cube.get(c1) == RED || cube.get(c2) == RED || cube.get(c3) == RED) {
            if (cube.get(c1) == GREEN || cube.get(c2) == GREEN || cube.get(c3) == GREEN) {
                if (cube.get(c1) == WHITE || cube.get(c2) == WHITE || cube.get(c3) == WHITE)
                    return 1;
                else if (cube.get(c1) == YELLOW || cube.get(c2) == YELLOW || cube.get(c3) == YELLOW)
                    return 3;
            } else if (cube.get(c1) == BLUE || cube.get(c2) == BLUE || cube.get(c3) == BLUE) {
                if (cube.get(c1) == WHITE || cube.get(c2) == WHITE || cube.get(c3) == WHITE)
                    return 2;
                else if (cube.get(c1) == YELLOW || cube.get(c2) == YELLOW || cube.get(c3) == YELLOW)
                    return 4;
            }
        } else if (cube.get(c1) == ORANGE || cube.get(c2) == ORANGE || cube.get(c3) == ORANGE) {
            if (cube.get(c1) == GREEN || cube.get(c2) == GREEN || cube.get(c3) == GREEN) {
                if (cube.get(c1) == WHITE || cube.get(c2) == WHITE || cube.get(c3) == WHITE)
                    return 5;
                else if (cube.get(c1) == YELLOW || cube.get(c2) == YELLOW || cube.get(c3) == YELLOW)
                    return 7;
            } else if (cube.get(c1) == BLUE || cube.get(c2) == BLUE || cube.get(c3) == BLUE) {
                if (cube.get(c1) == WHITE || cube.get(c2) == WHITE || cube.get(c3) == WHITE)
                    return 6;
                else if (cube.get(c1) == YELLOW || cube.get(c2) == YELLOW || cube.get(c3) == YELLOW)
                    return 8;
            }
        }
        return 0;
    }
    
    /**
     * Checks if the corner cubie orientations are correct. Corner twists that
     * are not in the correct orientation cannot be realized using legal twists.
     * @return true if corner cubie orientation is correct, false otherwise
     */
    public static boolean cornerParityTest() {
        
        List<Integer> one = new ArrayList<>();
        List<Integer> two = new ArrayList<>();
        int sum = 0;
        
        one.add(9);
        one.add(12);
        one.add(15);
        one.add(29);
        one.add(32);
        one.add(35);
        one.add(45);
        one.add(53);
        
        two.add(11);
        two.add(14);
        two.add(17);
        two.add(27);
        two.add(30);
        two.add(33);
        two.add(47);
        two.add(51);
        
        //Since the top and bottom are zero, it is unnecessary to add here
        for (int i = 0; i < 8; i++) {
            if (cube.get(one.get(i)) == RED || cube.get(one.get(i)) == ORANGE)
                sum += 1;
            if (cube.get(two.get(i)) == RED || cube.get(two.get(i)) == ORANGE)
                sum += 2;
        }
        
        //System.out.println(sum);
        return sum % 3 == 0;
    }
    
    /**
     * Checks if the edge cubie orientations are correct. Edge turns that are 
     * not in the correct orientation cannot be realized using legal edge turns.
     * @return true if edge cubie orientation is correct, false otherwise
     */
    public static boolean edgeParityTest() {
        
        int sum = 0;
        
        if (cube.get(1) == RED)
            sum += 1;
        if (cube.get(7) == RED)
            sum += 1;
        if (cube.get(10) == GREEN)
            sum += 1;
        if (cube.get(28) == GREEN)
            sum += 1;
        if (cube.get(21) == YELLOW)
            sum += 1;
        if (cube.get(23) == YELLOW)
            sum += 1;
        if (cube.get(16) == BLUE)
            sum += 1;
        if (cube.get(34) == BLUE)
            sum += 1;
        if (cube.get(37) == ORANGE)
            sum += 1;
        if (cube.get(43) == ORANGE)
            sum += 1;
        if (cube.get(48) == WHITE)
            sum += 1;
        if (cube.get(50) == WHITE)
            sum += 1;
        
        //System.out.println(sum);
        return sum % 2 == 0;
    }
    
}
