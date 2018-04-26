import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

/********************************************************************
 Written by: Nick Yeganeh
 Period: 7

Our secret encryption code:
Create an array of 8 integers and fill the array with random integers
between 0 and 5 (inclusive). For each character in the file add the
corresponding number to it's ASCII value. When you	get to the last
number in your array you should start back at the beginning. 
An example:

array: 2 1 6 3 4 3 0 3 
file: 				abcd
 					EFGH
 					1234
encrypted file: 	ccig	// add 2 to a, 1 to b, 6 to c and 3 to d
						IIGK 	// add 4 to E, 3 to F, 0 to G and 3 to H
						3397	// add 2 to 1, 1 to 2, 6 to 3 and 3 to 4

ASSIGNMENT: 							
 	Then your program should have a button menu that gives the user
 	the following options:

 		Print a file
 		Encrypt a file
 		Decrypt a file
 		Exit

 	print: ask for the name of the input file, read it & print it in a
 	 	JOP window. 
 	encrypt: ask for the name of the input and output files, read  
 		from the input file, encrypt it and write it to the output file.
  	decrypt: ask for the name of the input and output files, read  
 		from the input file, decrypt it and write it to the output file.
 	exit: your program should run until the user chooses option 4.

For all file names, just have the user input the file name. Add the
.txt automatically.

Remember to pattern your main method after the ones I have given you.
There shouldn't be much code - just method calls and a loop to keep the
program running until the user chooses to quit.

Once you get this working add the following:
	Ask the user whether they want to create a random key or read
	a key from a file. If they want to create a random key they 	
	should enter how many integers they want in the key and the file
 	name they want to save the key to. If they want to read from a file
 	they should enter the name of the file they want to read.

GRADING: (50 points)

 _____ Formatting (5 points)
 _____ Your program is structured correctly and your code is
  				well written (10 points)
 _____ Data files are read/written to correctly (10 points)
 _____ Encryption works correctly (10 points)
 _____ Decryption works correctly (10 points)
 _____ Key choice (random or file) works correctly (5 points)


 HAND IN:  Make a jar file, put it in my handin folder and 
  	print your program.

 ********************************************************************/
public class Encryption{
	public static void changeJOP()
	{
		UIManager.put("Label.font", new FontUIResource (new Font("Times New Roman"
				,Font.BOLD, 18)));
		UIManager.put("OptionPane.messageForeground", Color.white);
		UIManager.put("TextField.background", Color.gray);
		UIManager.put("TextField.font", new FontUIResource(new Font("Times New Roman"
				,Font.BOLD, 18)));
		UIManager.put("TextField.foreground", Color.white);
		UIManager.put("Panel.background", Color.gray);
		UIManager.put("OptionPane.background", Color.gray);
		UIManager.put("Button.background", Color.gray);
		UIManager.put("Button.foreground", Color.white);
		UIManager.put("Button.font", new FontUIResource	(new Font("Times New Roman "
				,Font.BOLD, 14)));
	}
	public static void main(String[] args)
	{
		changeJOP();
		
		instructions();
		
		int[] secretCode = createKey();

		int choice = menu();
		while(choice !=4)
		{
			switch(choice)
			{
			case 0: print(); break;
			case 1: encrypt(secretCode); break;
			case 2: decrypt(secretCode) ; break;
			case 3: instructions(); break;
			}
			choice = menu();
		}	
	}


	public static void instructions() {
		JOptionPane.showMessageDialog(null, "INSTRUCTIONS:\n"
				+ "Step One: Choosing a Key\n"
				+ "I would recommend for your firt time that you generate a new key, and save it to a file called 'key'.\n"
				+ "Don't worry about adding '.txt' when typing in the name of the file the file to save it in\n"
				+ "it's already done for you.\n\n"
				+ "Step Two: Choosing an Operation\n"
				+ "Now that you have generated or picked a new key you have come to choose between four options\n"
				+ "You can choose to Print a txt file, Encrypt a txt file using the key you generated/picked,\n"
				+ "Decrypt a txt file using the key you generated/picked, or Exit the program\n\n"
				+ "Printing: if you choose to print, the program will then ask you which file you want to print,\n"
				+ "and then display it on screen\n"
				+ "Encrypting: if you choose to encrypt, the program will ask you to choose the file you want to encrypt,\n"
				+ "then encrypt it using the key you generated/picked earlier in the program.\n"
				+ "I recommed the file you want to encrypt already have some sort of text in it otherwise there wont be \n"
				+ "anything to encrypt. Then it will ask you the name of the file you want to save the encrypted message to\n"
				+ "Decrypting: if you choose to decrypt, the program will ask you to choose the file you want to decrypt,\n"
				+ "then decrypt it using the key you generated/picked earlier in the program.\n"
				+ "I recommed the file you want to encrypt already have some sort of text in it otherwise there wont be \n"
				+ "anything to decrypt. Then it will ask you the name of the file you want to save the decrypted message to\n"
				+ "\nNOTE: You do not have to type '.txt' when it asks you for files, it is already done for you.");
	}
	public static int[] createKey()
	{
		int[] secretCode={};
		try{
			String[] choices = {"Generate a random one.", "I have my own key.", "Back to instructions"};
			int choice = JOptionPane.showOptionDialog
					(null, "Generate or pick key", "Encryption"
							,0,3, null, choices, null);
			String file = "";
			int length = 0;
			if(choice == 0){
				length = Integer.parseInt(JOptionPane
						.showInputDialog("What would you like the length of the key to be?:"));
				secretCode = new int[length];
				String file2 = JOptionPane
						.showInputDialog("What would you like to name the file that the key will be stored in?:");
				PrintWriter outFile = new PrintWriter
						(new FileOutputStream(file2 + ".txt"));
				for(int i = 0; i < length; i++){
					int randomInt = (int)(Math.random()*6);
					secretCode[i] = randomInt;
					outFile.print(randomInt+" ");
				}
				outFile.println();
				outFile.close();
			}
			if(choice == 1){		
				file = JOptionPane
						.showInputDialog("Enter file name:");
				Scanner inFile = new Scanner(new File(file + ".txt"));
				int next = 0;
				while(inFile.hasNextInt()){
					length++;
					next = inFile.nextInt();
				}
				inFile.close();
				secretCode = new int[length];
				int num = 0;
				Scanner otherInFile = new Scanner(new File(file + ".txt"));
				while(otherInFile.hasNextInt()){
					secretCode[num] = otherInFile.nextInt();
					num++;
				}
				otherInFile.close();
			}
			if (choice==2){
				instructions();
				createKey();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return secretCode;
		
	}


	public static int menu()
	{
		String[] choices = {"Print", "Encrypt", "Decrypt", "Back to Instructions", "Exit" };
		int choice = JOptionPane.showOptionDialog
				(null, "What would you like to do?", "Encryption",
						0, 3, null, choices, null);
		return choice;
	}


	public static void print()
	{
		String file = JOptionPane
				.showInputDialog("What file would you like to print?");
		try
		{
			Scanner inFile = new Scanner(new File(file + ".txt"));
			String word = "";
			while(inFile.hasNext())
			{
				word += inFile.nextLine()+"\n";
			}
			inFile.close();
			JOptionPane.showMessageDialog(null, word);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}


	public static void encrypt(int[] secretCode)
	{
		String line = "";
		String enter = JOptionPane
				.showInputDialog("What file would you like to encrypt?:");
		String output = JOptionPane
				.showInputDialog("What file would you like to save the encrypted message to?:");
		try
		{
			Scanner inFile = new Scanner
					(new File(enter+".txt"));
			PrintWriter outFile = new PrintWriter
					(new FileOutputStream(output+".txt"));

			while(inFile.hasNext())
			{
				line = inFile.nextLine();
				char[] array = new char[line.length()];
				for(int i = 0; i < line.length(); i++)
				{
					array[i] = (char) (line.charAt(i)+
							secretCode[i%secretCode.length]);
					outFile.print(array[i]);
				}
				outFile.println();
			}
			inFile.close();
			outFile.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}


	public static void decrypt(int[] secretCode)
	{
		String line = "";
		String enter = JOptionPane
				.showInputDialog("What file would you like to decrypt?;");
		String output = JOptionPane
				.showInputDialog("What file would you like to save the decrypted message to?:");
		try
		{
			Scanner inFile = new Scanner
					(new File(enter + ".txt"));
			PrintWriter outFile = new PrintWriter
					(new FileOutputStream(output + ".txt"));

			while(inFile.hasNext())
			{
				line = inFile.nextLine();
				char[] array = new char[line.length()];
				for(int i = 0; i < line.length(); i++)
				{
					array[i] = (char)(line.charAt
							(i)-secretCode[i%secretCode.length]);
					outFile.print(array[i]);
				}
				outFile.println();
			}
			inFile.close();
			outFile.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}