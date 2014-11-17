package rubik;

import java.util.ArrayList;
import java.util.Map;

/**
 * Keeps track of each state and its heuristic value from the breadth-first
 * search of all valid permutations of a cube.
 * @author Christopher Lee
 */
public class CubeNode implements Comparable<CubeNode> {
    
    protected byte[] state;
    protected int heuristic;
    protected int g;
    protected String path;
    
    /**
     * CubeNode constructor
     * @param state state of the cube
     * @param heuristic heuristic value
     */
    public CubeNode(byte[] state, int heuristic) {
        this.state = state;
        this.heuristic = heuristic;
    }
    
    /**
     * CubeNode alternate constructor
     * @param state state of the cube
     * @param heuristic heuristic value
     * @param path current path
     */
    public CubeNode(byte[] state, int heuristic, String path) {
        this.state = state;
        this.heuristic = heuristic;
        this.path = path;
    }
    
    /**
     * CubeNode alternate constructor
     * @param state state of the cube
     * @param heuristic heuristic value
     * @param g g value
     * @param path current path
     */
    public CubeNode(byte[] state, int heuristic, int g, String path) {
        this.state = state;
        this.heuristic = heuristic;
        this.g = g;
        this.path = path;
    }
    
    /**
     * Generates all the successors for a node (for IDA* search)
     * @param node node successors are generated for
     * @return ArrayList of successor CubeNodes
     */
    public static ArrayList<CubeNode> getSuccessors(CubeNode node) {
        ArrayList<CubeNode> successors = new ArrayList<>();
        for (Map.Entry<Byte, byte[]> face : Cube.FACES.entrySet()) {
            byte[] newState = Cube.rotate(node.state, face.getKey(), 1);
//            int encCorner = Integer.parseInt(KorfAlgorithm.encodeCorners(newState));
//            int encEdgesOne = Integer.parseInt(KorfAlgorithm.encodeFirstEdges(newState));
//            int encEdgesTwo = Integer.parseInt(KorfAlgorithm.encodeSecondEdges(newState));
            int encCorner = 0;
            int encEdgesOne = 0;
            int encEdgesTwo = 0;
            int[] heuristics = new int[3];
            
            heuristics[0] = IDAStar.cornerHeuristics[encCorner];
            heuristics[1] = IDAStar.edgeFirstHeuristics[encEdgesOne];
            heuristics[2] = IDAStar.edgeSecondHeuristics[encEdgesTwo];
            
            int max = heuristics[0];
            for (int i = 0; i < heuristics.length; i++) {
                if (heuristics[i] > max) 
                    max = heuristics[i];
            }
            
            successors.add(new CubeNode(newState, IDAStar.cornerHeuristics[encCorner], node.path + face.getKey() + "1"));
        }
        return successors;
    }

    /**
     * Overrided Comparable function for CubeNodes
     * @param t CubeNode being compared to
     * @return -1 if heuristic is lower, 1 if heuristic is greater, 0 if equal
     */
    @Override
    public int compareTo(CubeNode t) {
        if (this.heuristic < t.heuristic)
            return -1;
        else if (this.heuristic > t.heuristic)
            return 1;
        return 0;
    }
    
}
