package main;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

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

	// These private arrays represent basic moves of the Rubik's Cube. 
	// The front denoted by F, Right denoted by R, Up denoted by U.
	private static int[] F = {0,1,5,6,4,16,17,7,11,8,9,10,3,13,14,2,15,12,18,19,20,21,22,23};
    private static int[] Fi = {0, 1, 15, 12, 4, 2, 3, 7, 9, 10, 11, 8, 17, 13, 14, 16, 5, 6, 18, 19, 20, 21, 22, 23};
    private static int[] U = {3,0,1,2,8,9,6,7,12,13,10,11,20,21,14,15,16,17,18,19,4,5,22,23};
    private static int[] Ui = {1, 2, 3, 0, 20, 21, 6, 7, 4, 5, 10, 11, 8, 9, 14, 15, 16, 17, 18, 19, 12, 13, 22, 23};
    private static int[] R = {0,9,10,3,4,5,6,7,8,17,18,11,15,12,13,14,16,23,20,19,2,21,22,1};
    private static int[] Ri = {0, 23, 20, 3, 4, 5, 6, 7, 8, 1, 2, 11, 13, 14, 15, 12, 16, 9, 10, 19, 18, 21, 22, 17};
	
	// This method rotates the rubic cube by array.
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


	public static void printCube(char[] positions2) {
		System.out.println("     " + positions2[0] + " " + positions2[1]);
		System.out.println("     " + positions2[2] + " " + positions2[3]);
		
		System.out.println(positions2[8] + " " + positions2[9] + "  " + positions2[12] + " " + positions2[13] + "  " + positions2[20] + " " + positions2[21] + "  " + positions2[4] + " " + positions2[5]);
		System.out.println(positions2[10] + " " + positions2[11] + "  " + positions2[14] + " " + positions2[15] + "  " + positions2[22] + " " + positions2[23] + "  " + positions2[6] + " " + positions2[7]);
		
		System.out.println("     " + positions2[16] + " " + positions2[17]);
		System.out.println("     " + positions2[18] + " " + positions2[19]);
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
		System.out.println("Enter the cube state (top1 top2 top3 top4 left1 left2 left3 left4 front1 front2 front3 front4 right1 right2 right3 right4 bottom1 bottom2 bottom3 bottom4 back1 back2 back3 back4): ");
		
		Scanner inputCube = new Scanner(System.in);
		String[] positions = inputCube.nextLine().split(" ");
		
		char[] pos = new char[positions.length];
		
		for(int i = 0; i < positions.length; i++) {
			char position = positions[i].charAt(0);
			pos[i] = position;
		}
		
		System.out.println("Base Cube:");
		RubiksCubeState r = new RubiksCubeState(pos);
		RubiksCubeState.printCube(r.positions);
		
		String solutionPath = Solver.Solver(r);
		
		inputCube.close();
		
		if(solutionPath.equals("No solution, impossible configuration")){
			return;
		}
		else{
			System.out.println("\nTo solve cube apply these rotations: " + solutionPath+"\n");
			inputCube.close();
			System.out.println("Solved Cube:");
			RubiksCubeState end = new RubiksCubeState(pos);
			end.executeMoveSeq(solutionPath);
			RubiksCubeState.printCube(end.positions);
		}
	}
}
