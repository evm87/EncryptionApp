package EncryptionApp;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.swing.JSeparator;
import java.awt.Color;

public class AppInterface {

	private JFrame frame;
	private JTextField fileTextField;
	private JTextField pwTextField;
	Ciphers ciphers = new Ciphers();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AppInterface window = new AppInterface();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws FileNotFoundException 
	 */
	public AppInterface()
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("DataSafe v2.0");
		frame.setBounds(100, 100, 737, 454);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnEncrypt = new JButton("Encrypt");
		btnEncrypt.setBounds(211, 287, 97, 25);
		frame.getContentPane().add(btnEncrypt);
		
		JButton btnDecrypt = new JButton("Decrypt");
		btnDecrypt.setBounds(396, 287, 97, 25);
		frame.getContentPane().add(btnDecrypt);
		
		fileTextField = new JTextField();
		fileTextField.setBounds(328, 143, 116, 22);
		frame.getContentPane().add(fileTextField);
		fileTextField.setColumns(10);
		
		pwTextField = new JPasswordField();
		pwTextField.setBounds(328, 210, 116, 22);
		frame.getContentPane().add(pwTextField);
		pwTextField.setColumns(10);
		
		JLabel lblEnterFileName = new JLabel("Enter File Name:");
		lblEnterFileName.setBounds(222, 146, 104, 16);
		frame.getContentPane().add(lblEnterFileName);
		
		JLabel lblEnterPassword = new JLabel("Enter Password:");
		lblEnterPassword.setBounds(222, 213, 97, 16);
		frame.getContentPane().add(lblEnterPassword);
		
		JLabel lblNewLabel = new JLabel("DataSafe Encryption");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 48));
		lblNewLabel.setBounds(138, 24, 437, 95);
		frame.getContentPane().add(lblNewLabel);
		
    	btnEncrypt.addActionListener(new ActionListener() 
    	{
    		public void actionPerformed(ActionEvent a)
    		{
    			String fileName = fileTextField.getText();
    			//Not secure! Will change...
    			String fullPassword = pwTextField.getText();
    			
    			String directory = System.getProperty("user.dir");
    	        
    			File file = new File(directory);
    			
    			Path path = file.toPath();
    			
    			try 
    			{
					Ciphers.encrypt(fullPassword, path, fileName);
					JOptionPane.showMessageDialog(null,"File Encrypted Successfully");
    			} catch (NoSuchFileException e) {
    				JOptionPane.showMessageDialog(null,"File Not Found! Did you remember to add the file type? (like filename.txt)");
				} catch (InvalidKeyException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchPaddingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvalidAlgorithmParameterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalBlockSizeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (BadPaddingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
    			
    		}
    	});
    	
      	btnDecrypt.addActionListener(new ActionListener() 
    	{
    		public void actionPerformed(ActionEvent a)
    		{
    			String fileName = fileTextField.getText();
    			String fullPassword = pwTextField.getText();
    			
    			String directory = System.getProperty("user.dir");
    	        
    			File file = new File(directory);
    			
    			Path path = file.toPath();
    			
    			try 
    			{
					Ciphers.decrypt(fullPassword, path, fileName);
					JOptionPane.showMessageDialog(null,"File Decrypted Succesfully");
    			} catch (NoSuchFileException e) {
    				JOptionPane.showMessageDialog(null,"File Not Found! Did you remember to add .enc?");
				} catch (InvalidKeyException e) {
					JOptionPane.showMessageDialog(null,"Invalid Password or Corrupted File");
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchPaddingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvalidAlgorithmParameterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalBlockSizeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (BadPaddingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
    			
    		}
    	});
	}
}
