package main;
import java.util.Arrays;
import java.util.HashMap;

public class RubiksCubeState {

	char[] positions;
	boolean isNullState;
	
	public RubiksCubeState() {
		// create a solved cube
		positions = new char[24];
		for (int i = 0; i < 4; i++)
			positions[i] = 'w';
		for (int i = 4; i < 8; i++)
			positions[i] = 'o';
		for (int i = 8; i < 12; i++)
			positions[i] = 'g';
		for (int i = 12; i < 16; i++)
			positions[i] = 'r';
		for (int i = 16; i < 20; i++)
			positions[i] = 'y';
		for (int i = 20; i < 24; i++)
			positions[i] = 'b';
		this.isNullState = false;
	}
	
	public RubiksCubeState(char[] positions) {
		this.positions = positions;
		this.isNullState = false;
	}
	
	public RubiksCubeState(boolean nullState) {
        this.isNullState = nullState;
    }

	private static int[] F = {0,1,5,6,4,16,17,7,11,8,9,10,3,13,14,2,15,12,18,19,20,21,22,23};
    private static int[] Fi = {0, 1, 15, 12, 4, 2, 3, 7, 9, 10, 11, 8, 17, 13, 14, 16, 5, 6, 18, 19, 20, 21, 22, 23};
    private static int[] U = {3,0,1,2,8,9,6,7,12,13,10,11,20,21,14,15,16,17,18,19,4,5,22,23};
    private static int[] Ui = {1, 2, 3, 0, 20, 21, 6, 7, 4, 5, 10, 11, 8, 9, 14, 15, 16, 17, 18, 19, 12, 13, 22, 23};
    private static int[] R = {0,9,10,3,4,5,6,7,8,17,18,11,15,12,13,14,16,23,20,19,2,21,22,1};
    private static int[] Ri = {0, 23, 20, 3, 4, 5, 6, 7, 8, 1, 2, 11, 13, 14, 15, 12, 16, 9, 10, 19, 18, 21, 22, 17};
	

	public char[] rotateApply(int[] perm) {
		char[] newPositions = new char[24];
		for (int i = 0; i < 24; i++) {
			newPositions[i] = positions[perm[i]];
		}
		return newPositions;
	}
	
	public HashMap<String, RubiksCubeState> getReachableStates(){
		HashMap<String, RubiksCubeState> moves = new HashMap<>();
		addBasicMove("F'", F, moves);
		addBasicMove("F", Fi, moves);
		addBasicMove("U'", U, moves);
		addBasicMove("U", Ui, moves);
		addBasicMove("R'", R, moves);
		addBasicMove("R", Ri, moves);
		return moves;
	}
	
	private void addBasicMove(String name, int[] perm, HashMap<String, RubiksCubeState> moves) {
		RubiksCubeState state = new RubiksCubeState(rotateApply(perm));
		moves.put(name, state);
	}
	
	public void executeMoveSeq(String seq) {
		if (seq == null) return;
		String[] moves = seq.toUpperCase().split(" ");
		for (String move : moves) {
			switch(move) {
			case "F":
				positions = rotateApply(F);
				break;
			case "F'":
				positions = rotateApply(Fi);
				break;
				
			case "U":
				positions = rotateApply(U);
				break;
			case "U'":
				positions = rotateApply(Ui);
				break;
				
			case "R":
				positions = rotateApply(R);
				break;
			case "R'":
				positions = rotateApply(Ri);
				break;
			}
		}
	}

	@Override
	public int hashCode() {
		return Arrays.toString(this.positions).hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		RubiksCubeState state = (RubiksCubeState) obj;
		if (state.positions.length != this.positions.length) return false;
		for (int i = 0; i < this.positions.length; i++) {
			if (this.positions[i] != state.positions[i]) 
				return false;
		}
		return true;
	}
	
	public static void main(String[] args) {
		char[] pos = {'o', 'y', 'r', 'y', 'b', 'g', 'g', 'o', 'o', 'g', 'w', 'o', 'y', 'b', 'r', 'r', 'w', 'g', 'b', 'y', 'r', 'w', 'b', 'w'};
		
		System.out.println("Base Cube:");
		RubiksCubeState r = new RubiksCubeState(pos);
		RubiksCubeState.printCube(r.positions);
		
		String solutionPath = Solver.Solver(r);
		System.out.println("\nTo solve cube apply these rotations: " + solutionPath+"\n");
		
		System.out.println("Solved Cube:");
		RubiksCubeState end = new RubiksCubeState(pos);
		end.executeMoveSeq(solutionPath);
		RubiksCubeState.printCube(end.positions);
		
	}
}
