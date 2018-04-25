package EncryptionApp;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.JButton;
import javax.swing.JFileChooser;
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
	private JTextField filePathTextField;
	private JTextField pwTextField;
	Ciphers ciphers = new Ciphers();
	private JTextField fileNameTextField;

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
		frame = new JFrame("DataSafe v2.1");
		frame.setBounds(100, 100, 737, 454);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnEncrypt = new JButton("Encrypt");
		btnEncrypt.setBounds(211, 287, 97, 25);
		frame.getContentPane().add(btnEncrypt);
		
		JButton btnDecrypt = new JButton("Decrypt");
		btnDecrypt.setBounds(396, 287, 97, 25);
		frame.getContentPane().add(btnDecrypt);
		
		fileNameTextField = new JTextField();
		fileNameTextField.setBounds(328, 143, 116, 22);
		frame.getContentPane().add(fileNameTextField);
		fileNameTextField.setColumns(10);
		
		pwTextField = new JPasswordField();
		pwTextField.setBounds(328, 210, 116, 22);
		frame.getContentPane().add(pwTextField);
		pwTextField.setColumns(10);
		
		JLabel lblFileName = new JLabel("  File Path:");
		lblFileName.setBounds(253, 180, 66, 16);
		frame.getContentPane().add(lblFileName);
		
		JLabel lblEnterPassword = new JLabel("Enter Password:");
		lblEnterPassword.setBounds(221, 213, 97, 16);
		frame.getContentPane().add(lblEnterPassword);
		
		JLabel lblNewLabel = new JLabel("DataSafe Encryption");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 48));
		lblNewLabel.setBounds(138, 24, 437, 95);
		frame.getContentPane().add(lblNewLabel);
		
		/**
		 * Actions for the Search button. Retrieves a file and copies its path and name into the text fields.
		 */
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				JFileChooser chooser = new JFileChooser();

				if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
				{
					File selectedFile = chooser.getSelectedFile();
					String filePath = selectedFile.getAbsolutePath();
					
					fileNameTextField.setText(selectedFile.getName());
					filePathTextField.setText(filePath);
				}
				
			}
		});
		btnSearch.setBounds(467, 142, 97, 25);
		frame.getContentPane().add(btnSearch);
		
		filePathTextField = new JTextField();
		filePathTextField.setBounds(328, 178, 116, 22);
		frame.getContentPane().add(filePathTextField);
		filePathTextField.setColumns(10);
		
		JLabel lblFileName_1 = new JLabel(" File Name:");
		lblFileName_1.setBounds(249, 146, 83, 16);
		frame.getContentPane().add(lblFileName_1);
		
		
		/**
		 * Action for the encrypt button. Retrieves the working directory of the application to be sent to the Ciphers class along with the file name
		 * and password.
		 */
		
    	btnEncrypt.addActionListener(new ActionListener() 
    	{
    		public void actionPerformed(ActionEvent a)
    		{
    			String fileName = fileNameTextField.getText();
    			//Not secure! Will change...
    			String fullPassword = pwTextField.getText();
    			String filePath = filePathTextField.getText();
    	        
    			File file = new File(filePath);
    			
    			Path path = file.toPath();
    			
    			System.out.println("File Name: " + fileName);
    			System.out.println("File Path: " + path);
    			
    			try 
    			{
					Ciphers.encrypt(fullPassword, path, fileName);
					JOptionPane.showMessageDialog(null,"File Encrypted Successfully");
    			} catch (NoSuchFileException e) {
    				JOptionPane.showMessageDialog(null,"File Not Found!");
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
    	
    	/**
    	 * Actions for the decrypt button. Sends the current working directory of the application to the Ciphers class as well as
    	 * the file name and password.
    	 */
      	btnDecrypt.addActionListener(new ActionListener() 
    	{
    		public void actionPerformed(ActionEvent a)
    		{
    			String fileName = fileNameTextField.getText();
    			//Not secure! Will change...
    			String fullPassword = pwTextField.getText();
    			String filePath = filePathTextField.getText();
    	        
    			File file = new File(filePath);
    			
    			Path path = file.toPath();
    			
    			try 
    			{
					Ciphers.decrypt(fullPassword, path, fileName);
					JOptionPane.showMessageDialog(null,"File Decrypted Succesfully");
    			} catch (NoSuchFileException e) {
    				JOptionPane.showMessageDialog(null,"File Not Found!");
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
