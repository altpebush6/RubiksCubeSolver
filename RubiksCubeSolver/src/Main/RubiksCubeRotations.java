package Main;

public class RubiksCubeRotations {
	public static void rotateLeft(char[][] cube, String topBottom) {
		int index;
		if(topBottom.equals("Top")) {
			index = 0;
			
			char temp = cube[0][0];
			cube[0][0] =  cube[0][2];
			cube[0][2] =  cube[0][3];
			cube[0][3] =  cube[0][1];
			cube[0][1] = temp;
		}else {
			index = 2;
			
			char temp = cube[5][0];
			cube[5][0] =  cube[5][1];
			cube[5][1] =  cube[5][3];
			cube[5][3] =  cube[5][2];
			cube[5][2] = temp;
		}
		
		char temp1 = cube[1][index];
		char temp2 = cube[1][index + 1];
		
		for(int i = 0; i < 3; i++) {
			cube[i+1][index] = cube[i+2][index];
			cube[i+1][index + 1] = cube[i+2][index + 1];
		}
		
		cube[4][index] = temp1;
		cube[4][index + 1] = temp2;
	}
	
	public static void rotateRight(char[][] cube, String topBottom) {
		int index;
		if(topBottom.equals("Top")) {
			index = 0;
			
			char temp = cube[0][0];
			cube[0][0] =  cube[0][1];
			cube[0][1] =  cube[0][3];
			cube[0][3] =  cube[0][2];
			cube[0][2] = temp;
		}else {
			index = 2;
			
			char temp = cube[5][0];
			cube[5][0] =  cube[5][2];
			cube[5][2] =  cube[5][3];
			cube[5][3] =  cube[5][1];
			cube[5][1] = temp;
		}
		
		char temp1 = cube[4][index];
		char temp2 = cube[4][index + 1];
		
		for(int i = 2; i >= 0; i--) {
			cube[i+2][index] = cube[i+1][index];
			cube[i+2][index + 1] = cube[i+1][index + 1];
		}
		
		cube[1][index] = temp1;
		cube[1][index + 1] = temp2;
	}
	
	public static void rightRotateDown(char[][] cube) {
		char temp1 = cube[0][1];
		char temp2 = cube[0][3];
		
		cube[0][1] = cube[3][2];
		cube[0][3] = cube[3][0];
		
		cube[3][0] = cube[5][3];
		cube[3][2] = cube[5][1];
		
		cube[5][1] = cube[1][1];
		cube[5][3] = cube[1][3];
		
		cube[1][1] = temp1;
		cube[1][3] = temp2;
		
		char temp = cube[2][0];
		cube[2][0] =  cube[2][1];
		cube[2][1] =  cube[2][3];
		cube[2][3] =  cube[2][2];
		cube[2][2] = temp;
	}
	
	public static void rightRotateUp(char[][] cube) {
		char temp1 = cube[1][1];
		char temp2 = cube[1][3];
		
		cube[1][1] = cube[5][1];
		cube[1][3] = cube[5][3];
		
		cube[5][1] = cube[3][2];
		cube[5][3] = cube[3][0];
		
		cube[3][0] = cube[0][3];
		cube[3][2] = cube[0][1];
		
		cube[0][1] = temp1;
		cube[0][3] = temp2;
		
		char temp = cube[2][0];
		cube[2][0] =  cube[2][2];
		cube[2][2] =  cube[2][3];
		cube[2][3] =  cube[2][1];
		cube[2][1] = temp;
	}
	
	public static void leftRotateDown(char[][] cube) {
		char temp1 = cube[1][0];
		char temp2 = cube[1][2];
		
		cube[1][0] = cube[0][0];
		cube[1][2] = cube[0][2];
		
		cube[0][0] = cube[3][3];
		cube[0][2] = cube[3][1];
		
		cube[3][1] = cube[5][2];
		cube[3][3] = cube[5][0];
		
		cube[5][0] = temp1;
		cube[5][2] = temp2;
		
		char temp = cube[4][0];
		cube[4][0] =  cube[4][2];
		cube[4][2] =  cube[4][3];
		cube[4][3] =  cube[4][1];
		cube[4][1] = temp;
	}
	
	public static void leftRotateUp(char[][] cube) {
		char temp1 = cube[1][0];
		char temp2 = cube[1][2];
		
		cube[1][0] = cube[5][0];
		cube[1][2] = cube[5][2];
		
		cube[5][0] = cube[3][3];
		cube[5][2] = cube[3][1];
		
		cube[3][1] = cube[0][2];
		cube[3][3] = cube[0][0];
		
		cube[0][0] = temp1;
		cube[0][2] = temp2;
		
		char temp = cube[4][0];
		cube[4][0] =  cube[4][1];
		cube[4][1] =  cube[4][3];
		cube[4][3] =  cube[4][2];
		cube[4][2] = temp;
	}
	
	public static void rotateRearUp(char[][] cube) {
		char temp1 = cube[2][1];
		char temp2 = cube[2][3];
		
		cube[2][1] = cube[5][3];
		cube[2][3] = cube[5][2];
		
		cube[5][3] = cube[4][2];
		cube[5][2] = cube[4][0];
		
		cube[4][0] = cube[0][1];
		cube[4][2] = cube[0][0];	
		
		cube[0][0] = temp1;
		cube[0][1] = temp2;
		
		char temp = cube[3][0];
		cube[3][0] = cube[3][2];
		cube[3][2] = cube[3][3];
		cube[3][3] = cube[3][1];
		cube[3][1] = temp;
	}
	
	public static void rotateRearDown(char[][] cube) {
		char temp1 = cube[0][0];
		char temp2 = cube[0][1];
		
		cube[0][0] = cube[4][2];
		cube[0][1] = cube[4][0];
		
		cube[4][0] = cube[5][2];
		cube[4][2] = cube[5][3];
		
		cube[5][3] = cube[2][1];
		cube[5][2] = cube[2][3];	
		
		cube[2][1] = temp1;
		cube[2][3] = temp2;
		
		char temp = cube[3][0];
		cube[3][0] =  cube[3][1];
		cube[3][1] =  cube[3][3];
		cube[3][3] =  cube[3][2];
		cube[3][2] = temp;
	}
	
	public static void rotateFrontUp(char[][] cube) {
		char temp1 = cube[2][0];
		char temp2 = cube[2][2];
		
		cube[2][0] = cube[5][1];
		cube[2][2] = cube[5][0];
		
		cube[5][0] = cube[4][1];
		cube[5][1] = cube[4][3];
		
		cube[4][1] = cube[0][3];
		cube[4][3] = cube[0][2];	
		
		cube[0][2] = temp1;
		cube[0][3] = temp2;
		
		char temp = cube[1][0];
		cube[1][0] =  cube[1][1];
		cube[1][1] =  cube[1][3];
		cube[1][3] =  cube[1][2];
		cube[1][2] = temp;
	}
	
	public static void rotateFrontDown(char[][] cube) {
		char temp1 = cube[0][2];
		char temp2 = cube[0][3];
		
		cube[0][2] = cube[4][3];
		cube[0][3] = cube[4][1];
		
		cube[4][1] = cube[5][0];
		cube[4][3] = cube[5][1];
		
		cube[5][0] = cube[2][2];
		cube[5][1] = cube[2][0];	
		
		cube[2][0] = temp1;
		cube[2][2] = temp2;
		
		char temp = cube[1][0];
		cube[1][0] = cube[1][2];
		cube[1][2] = cube[1][3];
		cube[1][3] = cube[1][1];
		cube[1][1] = temp;
	}
}
