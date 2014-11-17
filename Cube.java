package rubik;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * A representation of a physical Rubik's Cube
 * @author Christopher Lee
 */
public class Cube {
    
    private static final byte RED = 0;
    private static final byte GREEN = 1;
    private static final byte YELLOW = 2;
    private static final byte BLUE = 3;
    private static final byte ORANGE = 4;
    private static final byte WHITE = 5;
    
    protected final static String GOAL_STATE = "RRRRRRRRRGGGYYYBBBGGGYYYBBBGGGYYYBBBOOOOOOOOOWWWWWWWWW";
    public byte[] cube;
    
    public final static byte[][] CORNERS = {
        {0, 9, 51},
        {2, 17, 53},
        {6, 11, 12},
        {8, 14, 15},
        {27, 42, 45},
        {35, 44, 47},
        {29, 30, 36},
        {32, 33, 38}
    };
    
    public final static byte[][] EDGES_1 = {
        {1, 52},
        {3, 10},
        {5, 16},
        {7, 13},
        {18, 48},
        {20, 21}
    };
    
    public final static byte[][] EDGES_2 = {
        {23, 24},
        {26, 50},
        {28, 39},
        {31, 37},
        {34, 41},
        {43, 46}
    };
    
    public final static HashMap<Byte, byte[]> FACES = new HashMap<>();
    public final static HashMap<Byte, byte[]> SIDES = new HashMap<>();
    
    /**
     * Default constructor initializes to a solved cube state
     */
    public Cube() {
        cube = new byte[54];
        for (int i = 0; i < GOAL_STATE.toCharArray().length; i++) {
            switch (GOAL_STATE.toCharArray()[i]) {
                case 'R':
                    cube[i] = RED;
                    break;
                case 'G':
                    cube[i] = GREEN;
                    break;
                case 'Y':
                    cube[i] = YELLOW;
                    break;
                case 'B':
                    cube[i] = BLUE;
                    break;
                case 'O':
                    cube[i] = ORANGE;
                    break;
                case 'W':
                    cube[i] = WHITE;
                    break;
                default:
                    break;
            }
        }
        init();
    }
    
    /**
     * Initializes the faces and sides HashMaps
     */
    private void init() {
        byte[] face = new byte[8];
        byte[] side = new byte[12];
        face[0] = 0;
        face[1] = 1;
        face[2] = 2;
        face[3] = 3;
        face[4] = 5;
        face[5] = 6;
        face[6] = 7;
        face[7] = 8;
        FACES.put(RED, face);
        face = new byte[8];
        face[0] = 9;
        face[1] = 10;
        face[2] = 11;
        face[3] = 18;
        face[4] = 20;
        face[5] = 27;
        face[6] = 28;
        face[7] = 29;
        FACES.put(GREEN, face);
        face = new byte[8];
        face[0] = 12;
        face[1] = 13;
        face[2] = 14;
        face[3] = 21;
        face[4] = 23;
        face[5] = 30;
        face[6] = 31;
        face[7] = 32;
        FACES.put(YELLOW, face);
        face = new byte[8];
        face[0] = 15;
        face[1] = 16;
        face[2] = 17;
        face[3] = 24;
        face[4] = 26;
        face[5] = 33;
        face[6] = 34;
        face[7] = 35;
        FACES.put(BLUE, face);
        face = new byte[8];
        face[0] = 36;
        face[1] = 37;
        face[2] = 38;
        face[3] = 39;
        face[4] = 41;
        face[5] = 42;
        face[6] = 43;
        face[7] = 44;
        FACES.put(ORANGE, face);
        face = new byte[8];
        face[0] = 45;
        face[1] = 46;
        face[2] = 47;
        face[3] = 48;
        face[4] = 50;
        face[5] = 51;
        face[6] = 52;
        face[7] = 53;
        FACES.put(WHITE, face);
        
        side[0] = 51;
        side[1] = 52;
        side[2] = 53;
        side[3] = 17;
        side[4] = 16;
        side[5] = 15;
        side[6] = 14;
        side[7] = 13;
        side[8] = 12;
        side[9] = 11;
        side[10] = 10;
        side[11] = 9;
        SIDES.put(RED, side);
        side = new byte[12];
        side[0] = 0;
        side[1] = 3;
        side[2] = 6;
        side[3] = 12;
        side[4] = 21;
        side[5] = 30;
        side[6] = 36;
        side[7] = 39;
        side[8] = 42;
        side[9] = 45;
        side[10] = 48;
        side[11] = 51;
        SIDES.put(GREEN, side);
        side = new byte[12];
        side[0] = 6;
        side[1] = 7;
        side[2] = 8;
        side[3] = 15;
        side[4] = 24;
        side[5] = 33;
        side[6] = 38;
        side[7] = 37;
        side[8] = 36;
        side[9] = 29;
        side[10] = 20;
        side[11] = 11;
        SIDES.put(YELLOW, side);
        side = new byte[12];
        side[0] = 8;
        side[1] = 5;
        side[2] = 2;
        side[3] = 53;
        side[4] = 50;
        side[5] = 47;
        side[6] = 44;
        side[7] = 41;
        side[8] = 38;
        side[9] = 32;
        side[10] = 23;
        side[11] = 14;
        SIDES.put(BLUE, side);
        side = new byte[12];
        side[0] = 30;
        side[1] = 31;
        side[2] = 32;
        side[3] = 33;
        side[4] = 34;
        side[5] = 35;
        side[6] = 45;
        side[7] = 46;
        side[8] = 47;
        side[9] = 27;
        side[10] = 28;
        side[11] = 29;
        SIDES.put(ORANGE, side);
        side = new byte[12];
        side[0] = 42;
        side[1] = 43;
        side[2] = 44;
        side[3] = 35;
        side[4] = 26;
        side[5] = 17;
        side[6] = 2;
        side[7] = 1;
        side[8] = 0;
        side[9] = 9;
        side[10] = 18;
        side[11] = 27;
        SIDES.put(WHITE, side);
    }
    
    /**
     * Constructor initializes from an existing text file
     * @param file file containing cube state
     * @throws java.io.IOException
     */
    public Cube(String file) throws IOException {
        this.cube = this.readFile(file);
    }
    
    /**
     * Handles file reading of cube state
     * @param filename file containing cube state
     * @return byte array of cube state
     * @throws FileNotFoundException 
     * @throws IOException 
     */
    public final byte[] readFile(String filename) throws FileNotFoundException, IOException {

        FileReader fr = new FileReader(filename);
        BufferedReader br = new BufferedReader(fr);
        String line = br.readLine();
        byte[] temp = new byte[54];

        while (line != null) {
            line = line.trim();
            char[] c = line.toCharArray();
            for (int i = 0; i < c.length; i++) {
                switch (c[i]) {
                    case 'R':
                        temp[i] = RED;
                        break;
                    case 'G':
                        temp[i] = GREEN;
                        break;
                    case 'Y':
                        temp[i] = YELLOW;
                        break;
                    case 'B':
                        temp[i] = BLUE;
                        break;
                    case 'O':
                        temp[i] = ORANGE;
                        break;
                    case 'W':
                        temp[i] = WHITE;
                        break;
                    default:
                        System.err.println("Unexpected character found - "
                                + "RubiksCube.readFile function");
                        break;
                }
            }
            line = br.readLine();
        }
        return temp;
    }
    
    /**
     * Handles cube rotations based on face and returns the state reached after
     * performing the rotation.
     * @param state original state of cube
     * @param face side the rotation is performed on
     * @param turns number of turns made
     * @return new state of the cube
     */
    public static byte[] rotate(byte[] state, Byte face, int turns) {
        
        if (turns % 4 == 0)
            return state;
        
        byte[] currentFace = FACES.get(face);
        byte[] faceSides = Cube.SIDES.get(face);
        
        if (currentFace == null || faceSides == null)
            return state;
        
        byte[] newState = state.clone();
        
//        for (int i = 0; i < currentFace.length; i++)
//            newState[currentFace[(i + (2*turns)) % 8]] = state[currentFace[i]];
//        
//        for (int j = 0; j < faceSides.length; j++) {
//            int temp = faceSides[(j + (3*turns)) % faceSides.length];
//            newState[temp] = state[faceSides[j]];
//        }
        
        switch (face) {
            case RED:
                for (int i = 0; i < turns; i++) {
                    newState[2] = state[currentFace[0]];
                    newState[5] = state[currentFace[1]];
                    newState[8] = state[currentFace[2]];
                    newState[7] = state[currentFace[4]];
                    newState[6] = state[currentFace[7]];
                    newState[3] = state[currentFace[6]];
                    newState[0] = state[currentFace[5]];
                    newState[1] = state[currentFace[3]];

                    newState[9] = state[faceSides[8]];
                    newState[10] = state[faceSides[7]];
                    newState[11] = state[faceSides[6]];
                    newState[12] = state[faceSides[5]];
                    newState[13] = state[faceSides[4]];
                    newState[14] = state[faceSides[3]];
                    newState[15] = state[faceSides[2]];
                    newState[16] = state[faceSides[1]];
                    newState[17] = state[faceSides[0]];
                    newState[53] = state[faceSides[11]];
                    newState[52] = state[faceSides[10]];
                    newState[51] = state[faceSides[9]];
                }
                break;
            case GREEN:
                for (int i = 0; i < turns; i++) {
                    newState[11] = state[currentFace[0]];
                    newState[20] = state[currentFace[1]];
                    newState[29] = state[currentFace[2]];
                    newState[28] = state[currentFace[4]];
                    newState[27] = state[currentFace[7]];
                    newState[18] = state[currentFace[6]];
                    newState[9] = state[currentFace[5]];
                    newState[10] = state[currentFace[3]];

                    newState[0] = state[faceSides[9]];
                    newState[3] = state[faceSides[10]];
                    newState[6] = state[faceSides[11]];
                    newState[12] = state[faceSides[0]];
                    newState[21] = state[faceSides[1]];
                    newState[30] = state[faceSides[2]];
                    newState[36] = state[faceSides[3]];
                    newState[39] = state[faceSides[4]];
                    newState[42] = state[faceSides[5]];
                    newState[45] = state[faceSides[6]];
                    newState[48] = state[faceSides[7]];
                    newState[51] = state[faceSides[8]];
                }
                break;
            case YELLOW:
                for (int i = 0; i < turns; i++) {
                    newState[14] = state[currentFace[0]];
                    newState[23] = state[currentFace[1]];
                    newState[32] = state[currentFace[2]];
                    newState[31] = state[currentFace[4]];
                    newState[30] = state[currentFace[7]];
                    newState[21] = state[currentFace[6]];
                    newState[12] = state[currentFace[5]];
                    newState[13] = state[currentFace[3]];

                    newState[6] = state[faceSides[9]];
                    newState[7] = state[faceSides[10]];
                    newState[8] = state[faceSides[11]];
                    newState[15] = state[faceSides[0]];
                    newState[24] = state[faceSides[1]];
                    newState[33] = state[faceSides[2]];
                    newState[38] = state[faceSides[3]];
                    newState[37] = state[faceSides[4]];
                    newState[36] = state[faceSides[5]];
                    newState[29] = state[faceSides[6]];
                    newState[20] = state[faceSides[7]];
                    newState[11] = state[faceSides[8]];
                }
                break;
            case BLUE:
                for (int i = 0; i < turns; i++) {
                    newState[17] = state[currentFace[0]];
                    newState[26] = state[currentFace[1]];
                    newState[35] = state[currentFace[2]];
                    newState[34] = state[currentFace[4]];
                    newState[33] = state[currentFace[7]];
                    newState[24] = state[currentFace[6]];
                    newState[15] = state[currentFace[5]];
                    newState[16] = state[currentFace[3]];

                    newState[2] = state[faceSides[11]];
                    newState[5] = state[faceSides[10]];
                    newState[8] = state[faceSides[9]];
                    newState[14] = state[faceSides[8]];
                    newState[23] = state[faceSides[7]];
                    newState[32] = state[faceSides[6]];
                    newState[38] = state[faceSides[5]];
                    newState[41] = state[faceSides[4]];
                    newState[44] = state[faceSides[3]];
                    newState[47] = state[faceSides[2]];
                    newState[50] = state[faceSides[1]];
                    newState[53] = state[faceSides[0]];
                }
                break;
            case ORANGE:
                for (int i = 0; i < turns; i++) {
                    newState[38] = state[currentFace[0]];
                    newState[41] = state[currentFace[1]];
                    newState[44] = state[currentFace[2]];
                    newState[43] = state[currentFace[4]];
                    newState[42] = state[currentFace[7]];
                    newState[39] = state[currentFace[6]];
                    newState[36] = state[currentFace[5]];
                    newState[37] = state[currentFace[3]];

                    newState[27] = state[faceSides[8]];
                    newState[28] = state[faceSides[7]];
                    newState[29] = state[faceSides[6]];
                    newState[30] = state[faceSides[9]];
                    newState[31] = state[faceSides[10]];
                    newState[32] = state[faceSides[11]];
                    newState[33] = state[faceSides[0]];
                    newState[34] = state[faceSides[1]];
                    newState[35] = state[faceSides[2]];
                    newState[45] = state[faceSides[5]];
                    newState[46] = state[faceSides[4]];
                    newState[47] = state[faceSides[3]];
                }
                break;
            case WHITE:
                for (int i = 0; i < turns; i++) {
                    newState[47] = state[currentFace[0]];
                    newState[50] = state[currentFace[1]];
                    newState[53] = state[currentFace[2]];
                    newState[52] = state[currentFace[4]];
                    newState[51] = state[currentFace[7]];
                    newState[48] = state[currentFace[6]];
                    newState[45] = state[currentFace[5]];
                    newState[46] = state[currentFace[3]];

                    newState[0] = state[faceSides[5]];
                    newState[1] = state[faceSides[4]];
                    newState[2] = state[faceSides[3]];
                    newState[9] = state[faceSides[6]];
                    newState[18] = state[faceSides[7]];
                    newState[27] = state[faceSides[8]];
                    newState[17] = state[faceSides[2]];
                    newState[26] = state[faceSides[1]];
                    newState[35] = state[faceSides[0]];
                    newState[42] = state[faceSides[9]];
                    newState[43] = state[faceSides[10]];
                    newState[44] = state[faceSides[11]];
                }
                break;
            default:
                
                break;
        }
        
        state = newState;
        return state;
        
    }
    
}

