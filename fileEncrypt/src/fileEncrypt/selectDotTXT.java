package fileEncrypt;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;
public class selectDotTXT {
	
	public selectDotTXT(){
		initialize();
	}

	/**
	 * Creates a window that allowing user to choose "encrypt" or "decrypt"
	 * Upon selection encryptFile() or decryptFile() is called.
	 */
	
	public void initialize(){
		JFrame frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
		
		JButton btnEncrypt = new JButton("Encrypt");
		frame.getContentPane().add(btnEncrypt);
		btnEncrypt.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				getSelectedEncrypt();	
			}});
				
		JButton btnNewButton = new JButton("Decrypt");
		frame.getContentPane().add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				getSelectedDecrypt();
			
			}});
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	/**
	 * Allows users to choose a *.txt  that they wish to encrypt.
	 * Creates a FileStreamCipher object when file has been chosen.
	 * Once encrypted a *.txt file with the Key Stream is created on the users desktop.
	 * (Windows machine only)
	 */
	public void getSelectedEncrypt(){
		
		JFileChooser fc = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("*.txt", "txt","text");
		fc.setFileFilter(filter);
		if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
			fileStreamCipher streamCiph = new fileStreamCipher(fc.getSelectedFile().getAbsolutePath());
			streamCiph.makeKeyStream(streamCiph.getBits());
			streamCiph.writeToFile(streamCiph.XorFunction(streamCiph.getBits()));
			
		
		}
	}
	
	/**
	 * Allows users to select the *.txt file that they wish to decrypt.
	 * Ask user to enter the KeyStream (sent to users desktop so they are able to copy in past)
	 * Creates a FileStreamCipher object when file has been chosen and key has been entered.
	 * If correct key has been entered, the program will decrypt the users chosen file 
	 */
	public void getSelectedDecrypt(){
		
		JFileChooser fc = new JFileChooser();
		
		FileNameExtensionFilter filter = new FileNameExtensionFilter("*.txt", "txt","text");
		fc.setFileFilter(filter);
		if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
		
			
			JFrame	frame = new JFrame();
			frame.setBounds(100, 100, 450, 300);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.getContentPane().setLayout(new GridLayout(2, 2));
			
			
			JTextArea textArea = new JTextArea();
			frame.getContentPane().add(textArea);
			frame.setVisible(true);
			JScrollPane sp = new JScrollPane(textArea);
			frame.getContentPane().add(sp);
			
			String fileDirectory =(fc.getSelectedFile().getAbsolutePath());	
			frame.setLocationRelativeTo(null);
			JButton btnNewButton = new JButton("Enter Key");
			frame.getContentPane().add(btnNewButton);
			btnNewButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){	
					fileStreamCipher file = new fileStreamCipher(fileDirectory,textArea.getText());
					String binaryEncrypted = file.readBinFromFile();
					String binaryDecrypted = file.XorFunction(binaryEncrypted);
					String asciiDecrypted = file.bytesToString(file.getByteFromBin(binaryDecrypted));
					file.writeToFile(asciiDecrypted);
					System.exit(0);
				}});
			
		}
	}
}

