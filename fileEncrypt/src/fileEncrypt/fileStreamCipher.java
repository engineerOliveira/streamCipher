package fileEncrypt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

public class fileStreamCipher {
		private String txtFileDirectory;	
		private String keyVal;
		
	/**Constructor for fileStreamCipher used for Encryption
	 * 
	 * @param s directory of the file selected
	 * 
	 */
	public fileStreamCipher(String s){
			this.txtFileDirectory= s;
	}
	
	/** constructor for fileStreamCipher used for Decryption
	 * 
	 * @param s directory of the file selected
	 * @param k Binary representation of the keyStream
	 * 
	 */
	public fileStreamCipher(String s,String k){
		this.txtFileDirectory= s;
		this.keyVal=k;
}


	/** 
	 * While using the file directory of the filStreamCipher object, this objects converts the Ascii characters inside a *.txt file .
	 * Method converts these Ascii characters into a byte array.
	 * 
	 * @return byte array of *.txt file
	 */
	private byte[] getBytesFromTxt(){	
		byte[] byteArray=null;
		
		FileInputStream fin = null;
		try{
			File file = new File(this.txtFileDirectory);
			fin = new FileInputStream(file);
			byteArray= new byte[(int) file.length()];
			fin.read(byteArray);
			fin.close();
		}catch(IOException e){
			// TODO Auto-generated catch block
						e.printStackTrace();
		}
		return byteArray;
	}
	
	/** 
	 * Converts an array of type byte into a String of its binary representation.
	 * 	
	 * @param b	byte array
	 * @return	binary representation of byte array.
	 */
	
	private String getBitsFromTxt(byte[] b){
		String x="";
		for(int i=0; i<b.length;i++){
			x= x + String.format("%8s", Integer.toBinaryString(b[i] & 0xFF)).replace(' ', '0');		
			
		}	
		return x;
	}
	
	/**
	 * Method returns the String of bits from the fileStreamCipher object by using helper methods;
	 * 
	 * @return String of binary
	 */
	
	public String getBits(){
		return getBitsFromTxt(getBytesFromTxt());
	}
	
	/**
	 *  Generates a KeyStream using Java's rand function. 
	 *  Method takes in a String of bits that 
	 *  
	 * @param bits
	 */
	  
	public void makeKeyStream(String bits){
	
		Random rn = new Random();
		int ranVal;
		String keyStream="";
		
		for(int i=0; i<bits.length();i++){
			ranVal = rn.nextInt(2);
			keyStream += ranVal;			
		}
		this.keyVal=keyStream;
		try {
		String userHomeFolder = System.getProperty("user.home")+"/Desktop";
		File textFile = new File(userHomeFolder,"ThisIsKey.txt");	
			BufferedWriter out= new BufferedWriter(new FileWriter(textFile));
			out.write(this.keyVal);
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
		
	/**
	 * Method that takes in a String of a binary sequence and performs the XOR operation with the KeyStream Value.
	 * 
	 * @param b String of a binary sequence
	 * @return returns the String of a Binary sequence after a XOR operation
	 */
	public String XorFunction(String b){
		
		String XOR="";	
		
		if(this.keyVal.length()!=b.length()){
			System.out.print("The length of the Key differs from the Binary string, program will shut down");
			System.exit(0);
		}
		
		for(int i=0; i<this.keyVal.length();i++  ){
			if (this.keyVal.charAt(i)=='1'& b.charAt(i)=='1'){
				XOR +="0";
			}else if (this.keyVal.charAt(i)=='0'& b.charAt(i)=='0'){
				XOR +="0";
			}else if(this.keyVal.charAt(i)=='1'& b.charAt(i)=='0'){
				XOR +="1";
			}else if(this.keyVal.charAt(i)=='0'& b.charAt(i)=='1'){
				XOR +="1";
			}else{ 
				System.out.print("This key is not in binary,program will shut down");
				System.exit(0);}
		}
		
	return XOR;
	}
	
	/**
	 * Method takes in a String of a binary sequence and converts binary sequence into a list of bytes
	 * 
	 * @param bin String of Binary sequence
	 * @return byte array after conversion
	 */
	public byte[] getByteFromBin(String bin){
		byte[] b = new byte[bin.length()/8];
		String holder;
		int startVal=0;
		int endVal=8;
		for (int i = 0; i< b.length;i++){
			holder = bin.substring(startVal,endVal);
			b[i]=(byte) Integer.parseInt(holder,2);
			startVal=endVal;
			endVal+=8;
			
		}
		return b;
	}

	/**Used to write the the *.txt file of the object created
	 * 
	 * @param s String to be written to file.
	 */
	public void writeToFile(String s) {
		
		File textFile = new File(this.txtFileDirectory);	
		BufferedWriter out;
		try {
			out = new BufferedWriter(new FileWriter(textFile));
			out.write(s);
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	/**
	 * Method is used to read and return the Binary String from the selected encrypted file
	 * @return String with Binary 
	 */
	public String readBinFromFile(){
		String content="";
		try {
			 content = new Scanner(new File(this.txtFileDirectory)).useDelimiter("\\Z").next();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(NoSuchElementException err){
			System.out.println("There is no text in the *.txt file, program will shut down");
			System.exit(0);
		}
		return content;
	}
	
	/**
	 * Method take in an array of bytes and returns the ascii representation
	 * @param b
	 * @return
	 */
			
	public String bytesToString(byte[] b){
		String s = new String(b);
		return s;	
	}
}

