/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rubik;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.PriorityQueue;

/**
 *
 * @author Chris
 */
public class IDAStar {
    
//    public static final byte[] cornerHeuristics = KorfAlgorithm.readFromBinaryFile("corners.txt", 88179840);
//    public static final byte[] edgeFirstHeuristics = KorfAlgorithm.readFromBinaryFile("edges1.txt", 42557920);
//    public static final byte[] edgeSecondHeuristics = KorfAlgorithm.readFromBinaryFile("edges2.txt", 42557920);
    public static final byte[] cornerHeuristics = new byte[88179840];
    public static final byte[] edgeFirstHeuristics = new byte[42557920];
    public static final byte[] edgeSecondHeuristics = new byte[42557920];
    public static PriorityQueue<CubeNode> frontier = new PriorityQueue<>();
    public static HashSet<CubeNode> explored = new HashSet<>();
    protected static int next;
    protected static int nodesSeen;
    
    /**
     * Performs IDA* search on the given Rubik's Cube state.
     * @param initState start state of cube
     * @return string representing optimal solution
     */
    public static String initialize(byte[] initState) {
        
        //CubeNode startNode = new CubeNode(initState, cornerHeuristics[Integer.parseInt(KorfAlgorithm.encodeCorners(initState))]);
        CubeNode startNode = new CubeNode(initState, 0);
        
        System.out.println("Beginning heuristic value: " + startNode.heuristic);
        
        next = startNode.heuristic;
        nodesSeen = 0;
        CubeNode endNode = null;
        
        while (endNode == null) {
            
            //System.out.println("Current: " + next);
            //System.out.println("Number of nodes seen: " + nodesSeen);
            
            frontier.add(startNode);
            nodesSeen++;
            Cube newCube = new Cube();
            
            while (!frontier.isEmpty()) {
                nodesSeen++;
                CubeNode currentNode = frontier.poll();
                
                if (Arrays.equals(currentNode.state, newCube.cube)) 
                    endNode = currentNode;
                
                explored.add(currentNode);
                ArrayList<CubeNode> successors = CubeNode.getSuccessors(currentNode);
                
                for (CubeNode s : successors) {
                    int temp = currentNode.g + s.heuristic;
                    s.g = currentNode.g + 1;
                    if (temp <= next && !explored.contains(s))
                        frontier.add(s);
                }
            }
            
            next++;
            frontier.clear();
            explored.clear();
            
        }
        
        //System.out.println("Solved!");
        //System.out.println("Total number of nodes seen: " + nodesSeen);
        
        return format(endNode.path);
        
    }
    
    private static String format(String s) {
        try {
            
            char[] c = s.toCharArray();
            String optSol = s.substring(0, 2);
            
            for (int i = 2; i < c.length; i++) {
                optSol += c[i];
                if (c[i] == c[i-2] && i % 2 == 0) {
                    Integer oldNum = Integer.parseInt(optSol.substring(optSol.length() - 2, optSol.length() - 1));
                    optSol = optSol.substring(0, optSol.length() - 2);
                    optSol += (oldNum + 1);
                    i++;
                }
            }
            
            return optSol;
            
        } catch (NumberFormatException e) {
            return s;
        }
    }
    
}
