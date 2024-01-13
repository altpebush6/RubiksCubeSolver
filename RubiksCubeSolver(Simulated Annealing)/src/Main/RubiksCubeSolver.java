package Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RubiksCubeSolver {
	
	public char[][] cube = {
			{'B','Y','W','W'},	// Top
			{'G','B','R','G'},
			{'O','O','W','O'},
			{'B','R','G','G'},
			{'Y','O','R','B'},
			{'W','R','Y','Y'}	// Bottom
	};
	
	public char[][] currentCube = new char[6][4];
	public char[][] nextCube = new char[6][4];
	
	Random random = new Random();
	
	char[] colors = {'G','W','O','Y','R','B'};
	
	public RubiksCubeSolver() {
		solver();
	}
	
    public char getRandomColor(char[] colors, HashMap<Character, Integer> colorCounts) {
    	
    	ArrayList<Character> availableColors = new ArrayList<>();
        for (char color : colors) {
            if (colorCounts.get(color) < 4) {
                availableColors.add(color);
            }
        }

        return availableColors.get(random.nextInt(availableColors.size()));
    }
	

	public void randomCube() {
		HashMap<Character, Integer> colorCounts = new HashMap<Character, Integer>();
        for (char color : colors) {
            colorCounts.put(color, 0);
        }
        
        
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                char randomColor = getRandomColor(colors, colorCounts);
                cube[i][j] = randomColor;
                colorCounts.put(randomColor, colorCounts.get(randomColor) + 1);
            }
        }
	}
	
	public int cost(char[][] cube) {
		int cost = 0;
		HashMap<Character, Integer> colorCounts = new HashMap<Character, Integer>();
		for(int i = 0; i < 6; i++) {
	        for (char color : colors) {
	            colorCounts.put(color, 0);
	        }
			for(int j = 0; j < 4; j++) {
				colorCounts.put(cube[i][j], colorCounts.get(cube[i][j]) + 1);
			}
			int max = 0;
			char maxColor;
	        for (char color : colors) {
	        	int colorCount = colorCounts.get(color);
	        	if(colorCount > max) {
	        		maxColor = color;
	        		max = colorCount;
	        	}
	        }
	        
	        cost += max;
		}
		
		return cost;
	}
	
	public void succesor_func() {
		int	randNumX, randNumY, randNumX2, randNumY2;
		
		copyCube(nextCube, currentCube);
		
		switch(random.nextInt(12) + 1) {
			case 1:
				RubiksCubeRotations.rotateLeft(nextCube, "Top");
				break;
			case 2:
				RubiksCubeRotations.rotateLeft(nextCube, "Bottom");
				break;
			case 3:
				RubiksCubeRotations.rotateRight(nextCube, "Top");
				break;
			case 4:
				RubiksCubeRotations.rotateRight(nextCube, "Bottom");
				break;
			case 5:
				RubiksCubeRotations.leftRotateDown(nextCube);
				break;
			case 6:
				RubiksCubeRotations.leftRotateUp(nextCube);
				break;
			case 7:
				RubiksCubeRotations.rightRotateDown(nextCube);
				break;
			case 8:
				RubiksCubeRotations.rightRotateUp(nextCube);
				break;
			case 9:
				RubiksCubeRotations.rotateRearDown(nextCube);
				break;
			case 10:
				RubiksCubeRotations.rotateRearUp(nextCube);
				break;
			case 11:
				RubiksCubeRotations.rotateFrontDown(nextCube);
				break;
			case 12:
				RubiksCubeRotations.rotateFrontUp(nextCube);
				break;
			}
	}
	
	public void solver() {
		printCube(cube);
		int oldE = cost(cube), newE;
		System.out.println("Initial Fault Score: "+oldE+"\n");
		
		copyCube(currentCube, cube);
		
		double tempMax = 1.0/3, tempMin = 0.0000001;
		double iMax = 1000, K = 1;
		int delta, iteration = 0;
		
		for(double temp = tempMax; temp >= tempMin; temp = next_temp(temp)){
			for(int i = 0; i < iMax; i++){
				
				iteration++;
				succesor_func();
				newE = cost(nextCube);
				K = 1;
				delta = newE - oldE;
				if(delta < 0) {
					if(Math.random() >= Math.exp(-delta / (K * temp))){
						undo_func(nextCube, currentCube); //rejected bad move
					}else {
						oldE = newE; //accepted bad move
						copyCube(currentCube, nextCube);
					}
				}else {
					oldE = newE; //always accept good moves
					copyCube(currentCube, nextCube);
				}
				
				/*
				if(iteration < 900) {
					if(iteration % 100 == 0) {
						System.out.println("Iteration: " + iteration + " - " + "Fault Score: " + newE);
					}
				}else {
					if(iteration % 25000 == 0) {
						System.out.println("Iteration: " + iteration + " - " + "Fault Score: " + newE);
					}
				}
				*/
				
				if(iteration % 100000 == 0) {
					System.out.println("Iteration: " + iteration + " - " + "Fault Score: " + newE);
					printCube(nextCube);
				}
				
				if(newE >= 24) {
					System.out.println("\nFault score of final solution: " + newE);
					System.out.println("Found in " + iteration + ". iteration");
					System.out.println("Final Solution:");
					printCube(nextCube);
					return;
				}
			}
		}
	}
	
	public void undo_func(char[][] arr1, char[][] arr2) {
		copyCube(arr1,arr2);
	}
	
	public double next_temp(double temp) {
		double cooling_rate = 0.9999999;
		return temp * cooling_rate; // decreases temp as much as cooling rate
	}
	
	public void copyCube(char[][] arr1, char[][] arr2) {
		for(int i = 0; i < 6; i++) {
			for(int j = 0; j < 4; j++) {
				arr1[i][j] = arr2[i][j];
			}
		}
	}
	
	public void printCube(char[][] cube) {
		System.out.println("     " + cube[0][0] + " " + cube[0][1]);
		System.out.println("     " + cube[0][2] + " " + cube[0][3]);
		
		System.out.println(cube[1][0] + " " + cube[1][1] + "  " + cube[2][0] + " " + cube[2][1] + "  " + cube[3][0] + " " + cube[3][1] + "  " + cube[4][0] + " " + cube[4][1]);
		System.out.println(cube[1][2] + " " + cube[1][3] + "  " + cube[2][2] + " " + cube[2][3] + "  " + cube[3][2] + " " + cube[3][3] + "  " + cube[4][2] + " " + cube[4][3]);
		
		System.out.println("     " + cube[5][0] + " " + cube[5][1]);
		System.out.println("     " + cube[5][2] + " " + cube[5][3]);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RubiksCubeSolver rb = new RubiksCubeSolver();
	}
}
