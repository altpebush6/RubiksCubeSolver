package main;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map.Entry;

public class Solver {
	public static String Solver(RubiksCubeState state) {
	    HashMap<RubiksCubeState, String> forwardParents = new HashMap<RubiksCubeState, String>();
	    HashMap<RubiksCubeState, String> backwardParents = new HashMap<RubiksCubeState, String>();
	    
	    ArrayDeque<RubiksCubeState> fqueue = new ArrayDeque<RubiksCubeState>();
	    ArrayDeque<RubiksCubeState> bqueue = new ArrayDeque<RubiksCubeState>();
	    
	    RubiksCubeState src = state, end = new RubiksCubeState();
	    forwardParents.put(end, null);
	    backwardParents.put(src, null);
	    fqueue.add(end);
	    fqueue.add(new RubiksCubeState(true));
	    bqueue.add(src);
	    
	  
	    
	    // check if cube already solved
	    if (end.equals(src))
	        return "Already solved";
	    
	    // bfs visit from both ends of graph
	    int graphRadius = 7;
	    for (int i = 0; i <= graphRadius; i++)
	    {
	        while (true) {
	            end = fqueue.remove();
	            if (end.isNullState) {
	                fqueue.add(new RubiksCubeState(true));
	                break;
	            }
	            
	            for (Entry<String, RubiksCubeState> move : end.getReachableStates().entrySet()) {
	                if (!forwardParents.containsKey(move.getValue())) {
	                    forwardParents.put(move.getValue(), move.getKey()); 
	                    fqueue.add(move.getValue());
	                }
	            }
	            
	            src = bqueue.remove();
	            
	            for (Entry<String, RubiksCubeState> move : src.getReachableStates().entrySet()) {
	                if (!backwardParents.containsKey(move.getValue())) {
	                    backwardParents.put(move.getValue(), move.getKey()); 
	                    bqueue.add(move.getValue());
	                }
	                // same state discovered in both ends of search
	                if (forwardParents.containsKey(move.getValue())) {
	                    String endpath = path(move.getValue(), forwardParents);
	                    String srcpath = path(move.getValue(), backwardParents);
	                    srcpath = reverse(srcpath);
	                    String solutionPath = srcpath + " " + endpath;
	                    return solutionPath;
	                }
	            }
	        }
        }
	    System.out.println("No solution, impossible configuration");
	    return "No solution, impossible configuration";
	   
	}
	private static String reverse(String path) {
	    path += " ";
	    String reverse = "";
	    
        for (int i = 0; i < path.length(); i++) {
            if (path.charAt(i) == ' ')
                reverse += "' ";
            else if (path.charAt(i) != '\'')
                reverse += path.charAt(i);
            else {
                reverse += " ";
                i++;
            }
        }
        
        String ar[] = reverse.split(" ");
        for (int i = 0; i < ar.length/2; i++) {
            String temp = ar[i];
            ar[i] = ar[ar.length-1-i];
            ar[ar.length-1-i] = temp;
        }
        
        return String.join(" ", ar);
	}
	
	private static String path(RubiksCubeState state, HashMap<RubiksCubeState, String> parents) {
	    String path = parents.get(state);
	    
	    RubiksCubeState next = new RubiksCubeState(state.positions);
        next.executeMoveSeq(path);
        
        while (parents.get(next) != null){
            path += " " + parents.get(next);
            next = new RubiksCubeState(state.positions);
            next.executeMoveSeq(path);
		}
        
        return path;
	}
	
}
