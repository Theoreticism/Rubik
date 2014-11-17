/*
 * Main class to initialize cube solve.
 */

package rubik;

import java.io.IOException;

/**
 * @author Christopher Lee
 */
public class Solve {
    
    public static void main(String[] args) throws IOException {
        
        if (args.length > 0) {
            KorfAlgorithm.cornerHeuristic();
            KorfAlgorithm.edgeFirstHeuristic();
            KorfAlgorithm.edgeSecondHeuristic();
            String file = args[0];
            Cube cube = new Cube(file);
            IDAStar.initialize(cube.cube);
        }
        
//        KorfAlgorithm.cornerHeuristic();
//        KorfAlgorithm.edgeFirstHeuristic();
//        KorfAlgorithm.edgeSecondHeuristic();
//        Cube cube = new Cube("cube01.txt");
//        IDAStar.initialize(cube.cube);
        
    }
    
}
