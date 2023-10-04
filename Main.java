import java.util.Scanner;

class Main {
  public static void main(String[] args) {
    Field myField = new Field(40,20,'.');
		Character myCharacter = new Character(8,'X');
		myField.makeField();
		myField.addCharacter(myCharacter, 0, 0);
		myField.renderField();
		myField.moveCharacterAround();
		
  }
}

class Field{
	private int width;
	private int length;
	private char fieldChar;
	private int fieldArea;
	private char[] field;
	private int characterStartingPosition;
	private int[] characterSymbolPositions;
	Character character;
	Scanner myScanner;
	
	public Field(int width, int length, char fieldChar){
		this.width = width;
		this.length = length;
		this.fieldChar = fieldChar;
	}

	public void makeField(){
		this.fieldArea = width * length;
		this.field = new char[fieldArea];
		for(int i = 0; i < fieldArea; i++) field[i] = fieldChar;
	}

	public void renderField(){
		String stringField = "";
		for(int i = 0; i < fieldArea; i++){
			if (i%width == 0) stringField+="\n";
			stringField+=field[i];
		} 
		System.out.println(stringField);
	}
	
	public void addCharacter(Character character, int x, int y){
		if (character.getSymbol() == this.fieldChar){
			System.out.println("Error, the character is made up of the same character as the field.");
			return;
		}
		this.character = character;
		char characterSymbol = character.getSymbol();
		int characterLength = character.getLength();
		this.characterStartingPosition = x + y*this.width;
		int startingPosition = this.characterStartingPosition;
		this.characterSymbolPositions = new int [characterLength];
		for(int i = 0; i < characterLength; i++){
			this.field[startingPosition] = characterSymbol;
			this.characterSymbolPositions[i] = startingPosition;
			startingPosition+=this.width;
		}
	}

	public char processUserInput(char direction){
		char capitalizedDirection;
		int asciiDirection = (int)direction;
		if(asciiDirection >= 97) capitalizedDirection = (char)(asciiDirection-32);
		else capitalizedDirection = direction;
		return capitalizedDirection;
	}

	public void moveCharacter(char direction){
		if(direction == 'W'){
			this.newCharacterPositionsUp(characterSymbolPositions);
			
		}
	}

	public void closeField(){
		myScanner.close();
	}

	private int[] fieldBorder(){
		
		//An array containing the index values that symbol the border of the field
		return new int[2];
	}	


	public char getUserInput(){
		myScanner = new Scanner(System.in);
		System.out.println("Select a direction: W (up), S (down), A (left), D (right): ");
		char input = myScanner.nextLine().charAt(0);
		return this.processUserInput(input);
	}

	public void moveCharacterAround(){
		//Make it so that the previous character symbol has to move into the position of the current character symbol, calculate the new character symbol position and move each symbol from its previous position into the new one
		// int[] corners = this.fieldCorners();
		// boolean right = false;
		// boolean left = false;
		// boolean up = false;
		// boolean down = false;
		while(true){
			char direction = this.getUserInput();
			int lastPosition = this.characterSymbolPositions[this.characterSymbolPositions.length-1];
			// int characterHead = this.characterSymbolPositions[0];
			
			// if (characterHead == corners[0] || right){
				
			// 	right = true;
			// 	left = false;
			// 	up = false;
			// 	down = false;
			// };
			// if (characterHead == corners[1]){
			// 	right = false;
			// 	left = false;
			// 	up = false;
			// 	down = true;
			// };
			// if (characterHead == corners[2]){
			// 	right = false;
			// 	left = false;
			// 	up = true;
			// 	down = false;
			// }; 
			// if (characterHead == corners[3]){
			// 	right = false;
			// 	left = true;
			// 	up = false;
			// 	down = false;			
			// };
			
			if (direction == 'D')this.characterSymbolPositions = this.newCharacterPositionsRight(this.characterSymbolPositions);
			if (direction == 'S')this.characterSymbolPositions = this.newCharacterPositionsDown(this.characterSymbolPositions);
			if (direction == 'W')this.characterSymbolPositions = this.newCharacterPositionsUp(this.characterSymbolPositions);
			if (direction == 'A')this.characterSymbolPositions = this.newCharacterPositionsLeft(this.characterSymbolPositions);
			
			for (int i = 0; i < this.characterSymbolPositions.length; i++){
				this.field[this.characterSymbolPositions[i]] = this.character.getSymbol();
			}
			this.field[lastPosition] = this.fieldChar;
			this.renderField();
	 	}
		
		
	}

	private int[] fieldCorners(){
		//Returns an array that is just the 4 corners of the field array, will be used to compare the position of the head at any given moment and prevent it from going "through" the board 
		int upperLeft = 0;
		int upperRight = this.width-1;
		int lowerLeft = upperLeft + (this.width*(this.length-1));
		int lowerRight = upperRight + (this.width*(this.length-1));
		return new int[]{upperLeft, upperRight, lowerLeft, lowerRight};
	}
	public int moveCharacterSymbolDown(int currentPosition){
		return currentPosition + this.width;
	}

	public int[] newCharacterPositionsRight(int[] currentPositions){
		int[] newPositions = new int [currentPositions.length];
		for(int i = 0; i < currentPositions.length-1; i++){
				if (i == 0) newPositions[i] = currentPositions[i]+1;
				newPositions[i+1] = currentPositions[i];
		}
		return newPositions;
	}
	
	public int[] newCharacterPositionsDown(int[] currentPositions){
		int[] newPositions = new int [currentPositions.length];
		for(int i = 0; i < currentPositions.length-1; i++){
				if (i == 0) newPositions[i] = currentPositions[i]+this.width;
				newPositions[i+1] = currentPositions[i];
		}
		return newPositions;
	}
	
	public int[] newCharacterPositionsLeft(int[] currentPositions){
		int[] newPositions = new int [currentPositions.length];
		for(int i = 0; i < currentPositions.length-1; i++){
				if (i == 0) newPositions[i] = currentPositions[i]-1;
				newPositions[i+1] = currentPositions[i];
		}
		return newPositions;
	}

	public int[] newCharacterPositionsUp(int[] currentPositions){
		int[] newPositions = new int [currentPositions.length];
		for(int i = 0; i < currentPositions.length-1; i++){
				if (i == 0) newPositions[i] = currentPositions[i]-this.width;
				newPositions[i+1] = currentPositions[i];
		}
		return newPositions;
	}
}


class Character{
	private int length;
	private char characterSymbol;
	
	public Character(int length, char characterSymbol){
		this.length = length;
		this.characterSymbol = characterSymbol;
	}
	public char getSymbol(){
		return this.characterSymbol;
	}
	public int getLength(){
		return this.length;
	}
	
}