package rubik;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * This class provides methods for generating the three heuristic tables for 
 * finding the optimal solution to solve a Rubik's Cube. The heuristic tables
 * are as follows: the corner heuristic table, the first half of the edge 
 * heuristic table, and the second half of the edge heuristic table. The
 * heuristic tables are stored in byte arrays and exported as byte files.
 * @author Christopher Lee
 */
public class KorfAlgorithm {
    
    /**
     * The position and orientation of the last cubie is determined by the other
     * seven. Therefore, (8!)*(3^7) = 88,179,840 unique states.
     */
    private static final int MAX_CORNER_STATES = 88179840;
    
    /**
     * Using all twelve edges mapped in too large of a file, so it is split in 
     * half. Therefore, (12!)/(6!)*(2^6) = 42,557,920 unique states.
     */
    private static final int MAX_EDGE_STATES = 42557920;
    
    /**
     * The maximum number of moves required to solve each combination of corner
     * cubies.
     */
    private static final int CORNER_DEPTH_LIMIT = 11;
    
    /**
     * The maximum number of moves required to solve half of the total amount of
     * edge cubies (6).
     */
    private static final int EDGE_DEPTH_LIMIT = 10;
    
    protected static byte[] cornerHeuristic = new byte[MAX_CORNER_STATES];
    protected static byte[] edgeFirstHeuristic = new byte[MAX_EDGE_STATES];
    protected static byte[] edgeSecondHeuristic = new byte[MAX_EDGE_STATES];
    
    /**
     * Generates corner heuristics for the Rubik's Cube using Korf's Algorithm 
     * and a breadth-first search.
     */
    public static void cornerHeuristic() {
    
        //0-9-51
        //2-17-53
        //6-11-12
        //8-14-15
        //27-42-45
        //35-44-47
        //29-30-36
        //32-33-38
        
        //Start with a solved cube
        Cube cube = new Cube();
        Queue<CubeNode> queue = new LinkedList<>();
        
        queue.add(new CubeNode(cube.cube, 0));
        cornerHeuristic = new byte[MAX_CORNER_STATES];
        Set<Map.Entry<Byte, byte[]>> faces = Cube.FACES.entrySet();
        
        while(!queue.isEmpty()) {
            //System.out.println(Arrays.toString(queue.peek().state));
            CubeNode current = queue.poll();
            
            for (Map.Entry<Byte, byte[]> face: faces) {
                byte[] newState = Cube.rotate(current.state, face.getKey(), 1);
                int enCorner = Integer.parseInt(encodeCorners(newState));
                
                if (cornerHeuristic[enCorner] == 0)
                    queue.add(new CubeNode(newState, current.heuristic + 1));
                
            }
            
            String enCorner = encodeCorners(current.state);
            int enCornerInt = Integer.parseInt(enCorner);
            if (cornerHeuristic[enCornerInt] == 0) {
                cornerHeuristic[enCornerInt] = (byte)current.heuristic;
                System.out.println(enCorner + "," + current.heuristic);
            }
        }
        
        //writeToBinaryFile(cornerHeuristic, "corners.txt");
        
    }
    
    /**
     * Generates edge heuristics for the Rubik's Cube using Korf's Algorithm 
     * and a breadth-first search. This applies only to the first six edges.
     */
    public static void edgeFirstHeuristic() {
        
        //1-52
        //3-10
        //5-16
        //7-13
        //18-48
        //20-21
        
        //Start with a solved cube
        Cube cube = new Cube();
        Queue<CubeNode> queue = new LinkedList<>();
        
        queue.add(new CubeNode(cube.cube, 0));
        edgeFirstHeuristic = new byte[MAX_EDGE_STATES];
        Set<Map.Entry<Byte, byte[]>> faces = Cube.FACES.entrySet();
        
        while (!queue.isEmpty()) {
            CubeNode current = queue.poll();
            
            for (Map.Entry<Byte, byte[]> face: faces) {
                byte[] newState = Cube.rotate(current.state, face.getKey(), 1);
                int enEdge = Integer.parseInt(encodeFirstEdges(newState));
                String temp = String.valueOf(enEdge);
                
                for (int i = 0; i < temp.length(); i++) {
                    int j = Character.digit(temp.charAt(i), 10);
                    if (edgeFirstHeuristic[j] == 0)
                        queue.add(new CubeNode(newState, current.heuristic + 1));
                }
            }
            
            String enEdge = encodeFirstEdges(current.state);
            int enEdgeInt = Integer.parseInt(enEdge);
            if (edgeFirstHeuristic[enEdgeInt] == 0) {
                edgeFirstHeuristic[enEdgeInt] = (byte)current.heuristic;
                //System.out.println(enEdge + "," + current.heuristic);
            }
        } 
        
        //writeToBinaryFile(edgeFirstHeuristic, "edges1.txt");
        
    }
    
    /**
     * Generates edge heuristics for the Rubik's Cube using Korf's Algorithm 
     * and a breadth-first search. This applies only to the second six edges.
     */
    public static void edgeSecondHeuristic() {
        
        //23-24
        //26-50
        //31-37
        //28-39
        //34-41
        //43-46
        
        //Start with a solved cube
        Cube cube = new Cube();
        Queue<CubeNode> queue = new LinkedList<>();
        
        queue.add(new CubeNode(cube.cube, 0));
        edgeSecondHeuristic = new byte[MAX_EDGE_STATES];
        Set<Map.Entry<Byte, byte[]>> faces = Cube.FACES.entrySet();
        
        while (!queue.isEmpty()) {
            CubeNode current = queue.poll();
            
            for (Map.Entry<Byte, byte[]> face: faces) {
                byte[] newState = Cube.rotate(current.state, face.getKey(), 1);
                int enEdge = Integer.parseInt(encodeSecondEdges(newState));
                String temp = String.valueOf(enEdge);
                
                for (int i = 0; i < temp.length(); i++) {
                    int j = Character.digit(temp.charAt(i), 10);
                    if (edgeSecondHeuristic[j] == 0)
                        queue.add(new CubeNode(newState, current.heuristic + 1));
                }
            }
            
            String enEdge = encodeSecondEdges(current.state);
            int enEdgeInt = Integer.parseInt(enEdge);
            if (edgeSecondHeuristic[enEdgeInt] == 0) {
                edgeSecondHeuristic[enEdgeInt] = (byte)current.heuristic;
                //System.out.println(enEdge + "," + current.heuristic);
            }
        }
        
        //writeToBinaryFile(edgeSecondHeuristic, "edges2.txt");
        
    }
    
    /**
     * Writes the heuristic value contained in a byte table to a text file.
     * @param byteArray byte array containing heuristic values.
     * @param filename specified destination file.
     */
    private static void writeToBinaryFile(byte[] table, String filename) {
        
        int lineCount = 0;
        
        try {
            
//            FileOutputStream fos = new FileOutputStream(filename);
//            fos.write(table);
//            fos.flush();
//            fos.close();
            
            PrintWriter pw = new PrintWriter(new BufferedOutputStream(new FileOutputStream(filename)));
            
            for (int i = 0; i < table.length; i+=2) {
                byte b1 = (byte)(table[i] | table[i+1]);
                pw.print(b1);
                lineCount += 1;
            }
            
        } catch (FileNotFoundException e) {
            System.err.println("Error occurred while writing heuristic to file at line " + lineCount);
        }
        
    }
    
    /**
     * Reads the heuristic values from a file and stores them as bytes in a 
     * byte table.
     * @param filename binary file containing heuristic
     * @param tSize size of byte array
     * @return 
     */
    protected static byte[] readFromBinaryFile(String filename, int tSize) {
        
        byte[] heuristic = new byte[tSize];
        
        try {
            
            FileReader fr = new FileReader(filename);
            BufferedReader bf = new BufferedReader(fr);
            
            for (int i = 0; i < tSize; i++) {
                heuristic[i] = Byte.parseByte(bf.readLine());
            }
        
        } catch (IOException e) {
            System.err.println("Error occurred while reading heuristic from file: " + filename);
        }
        
        return heuristic;
                    
    }
    
    /**
     * Maps corners to goal state and handles variable-based encoding for corners.
     * @param state current state of the cube
     * @return encoded corner state
     */
    protected static String encodeCorners(byte[] state) {
        
        String[] encoded = new String[8]; //8 corners
        byte[][] corners = Cube.CORNERS;
        HashMap<Integer, Integer> mapped = new HashMap<>();
        HashMap<String, Integer> goal = new HashMap<>();

        goal.put("015", 0); //RED WHITE GREEN
        goal.put("035", 1); //RED WHITE BLUE
        goal.put("012", 2); //RED YELLOW GREEN
        goal.put("023", 3); //RED YELLOW BLUE
        goal.put("124", 4); //ORANGE YELLOW GREEN
        goal.put("234", 5); //ORANGE YELLOW BLUE
        goal.put("145", 6); //ORANGE WHITE GREEN
        goal.put("345", 7); //ORANGE WHITE BLUE

        for(int i = 0; i < corners.length; i++) {
            String temp = "";

            for (int s : corners[i])
                temp += state[s];

            char[] c = temp.toCharArray();
            Arrays.sort(c);
            mapped.put(goal.get(new String(c)), i);
        }

        for (int j = 0; j < mapped.size(); j++) {
            int difference = 0;
            int aCorner = mapped.get(j);
            
            for (int k = 0; k < j; k++) {
                if (mapped.get(j) < aCorner)
                    difference++;
            }
            encoded[j] = Integer.toString(aCorner - difference);
        }
        
        StringBuilder sb = new StringBuilder();
        for (String encoded1 : encoded)
            sb.append(encoded1);
        
        return sb.toString();
        
    }
    
    /**
     * Maps edges to goal state and handles variable-based encoding for edges.
     * This applies to the first six edges only.
     * @param state current state of the cube
     * @return encoded edge (first set) state
     */
    protected static String encodeFirstEdges(byte[] state) {
        
        byte[][] edges = Cube.EDGES_1;
        HashMap<Integer, Integer> mapped = new HashMap<>();
        HashMap<String, Integer> goal = new HashMap<>();
        
        goal.put("05", 0); //RED WHITE
        goal.put("01", 1); //RED GREEN
        goal.put("03", 2); //RED BLUE
        goal.put("02", 3); //RED YELLOW
        goal.put("15", 4); //GREEN WHITE
        goal.put("12", 5); //GREEN YELLOW
        
        for (int i = 0; i < edges.length; i++) {
            String temp = "";
            
            for (int s: edges[i])
                temp += state[s];
            
            char[] c = temp.toCharArray();
            Arrays.sort(c);
            mapped.put(goal.get(new String(c)), i);
        }
        
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < mapped.size(); j++) 
            sb.append(mapped.get(j));
        
        return sb.toString();
        
    }
    
    /**
     * Maps edges to goal state and handles variable-based encoding for edges.
     * This applies to the second six edges only.
     * @param state current state of the cube
     * @return encoded edge (second set) state
     */
    protected static String encodeSecondEdges(byte[] state) {
        
        byte[][] edges = Cube.EDGES_2;
        HashMap<Integer, Integer> mapped = new HashMap<>();
        HashMap<String, Integer> goal = new HashMap<>();
        
        goal.put("23", 0); //BLUE YELLOW
        goal.put("35", 1); //BLUE WHITE
        goal.put("14", 2); //ORANGE GREEN
        goal.put("24", 3); //ORANGE YELLOW
        goal.put("34", 4); //ORANGE BLUE
        goal.put("45", 5); //ORANGE WHITE
        
        for (int i = 0; i < edges.length; i++) {
            String temp = "";
            
            for (int s: edges[i])
                temp += state[s];
            
            char[] c = temp.toCharArray();
            Arrays.sort(c);
            mapped.put(goal.get(new String(c)), i);
        }
        
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < mapped.size(); j++) 
            sb.append(mapped.get(j));
        
        return sb.toString();
        
    }
    
//    public static void main(String[] args) {
        //cornerHeuristic();
        //edgeFirstHeuristic();
        //edgeSecondHeuristic();
        
        //writeToBinaryFile(cornerHeuristic, "corners.txt");
        //writeToBinaryFile(edgeFirstHeuristic, "edge1.txt");
        //writeToBinaryFile(edgeSecondHeuristic, "edge2.txt");
        
//    }
    
}
