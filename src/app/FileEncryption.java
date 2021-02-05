package app;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.security.Key;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class FileEncryption implements ActionListener{

	String path="";
	boolean isEncrypted=false;
	FileEncryptionFrame frame;
	int index;
	FileEncryption(FileEncryptionFrame frame,int index,String path,boolean isEncrypted) {
		this.path=path;
		this.isEncrypted=isEncrypted;
		this.frame=frame;
		this.index=index;
	}
	public boolean encryptFile(String key, File file) {
		return doCrypto(Cipher.ENCRYPT_MODE, key, file);
	}

	public boolean decryptFile(String key, File file) {
		return doCrypto(Cipher.DECRYPT_MODE, key, file);
	}

	private static boolean doCrypto(int cipherMode, String key, File file) {
		try {
			Key secretKey = new SecretKeySpec(key.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(cipherMode, secretKey);

			FileInputStream inputStream = new FileInputStream(file);
			byte[] inputBytes = new byte[(int) file.length()];
			inputStream.read(inputBytes);

			byte[] outputBytes = cipher.doFinal(inputBytes);

			FileOutputStream outputStream = new FileOutputStream(file);
			outputStream.write(outputBytes);

			inputStream.close();
			outputStream.close();

			return true;
		} 
		catch(FileNotFoundException exp) {
			JOptionPane.showMessageDialog(null, "File not found on location","Error",JOptionPane.ERROR_MESSAGE);

		}
		catch(BadPaddingException exp) {
			JOptionPane.showMessageDialog(null, "Incorrect password","Error",JOptionPane.ERROR_MESSAGE);
		}
		catch (Exception exp) {
			exp.printStackTrace();
		}
		return false;
	}

	public static void main(String[] args) {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		File file=new File(path);
		if(!isEncrypted) {
			System.out.println(file.getAbsolutePath());
			String password=JOptionPane.showInputDialog(null, "Set 8 digit password", "Password", JOptionPane.QUESTION_MESSAGE);
			if(password==null) {
				return;
			}
			if(password.length()!=8) {
				JOptionPane.showMessageDialog(null, "Password length must be equal to 8 digit","Error",JOptionPane.ERROR_MESSAGE);
				return ;
			}
			System.out.println(password);
			boolean success=this.encryptFile(password+password, file);
			if(!success) {
				return;
			}
			System.out.println(success);
			frame.fileData.set(index, path+",YES");
			
		
		} else {
			System.out.println(file.getAbsolutePath());
			String password=JOptionPane.showInputDialog(null, "Enter 8 digit password", "Password", JOptionPane.QUESTION_MESSAGE);
			if(password==null) {
				return;
			}
			if(password.length()!=8) {
				JOptionPane.showMessageDialog(null, "Password length must be equal to 8 digit","Error",JOptionPane.ERROR_MESSAGE);
				return ;
			}
			System.out.println(password);
			boolean success=this.decryptFile(password+password, file);
			System.out.println(success);
			if(!success) {
				return;
			}
			frame.fileData.set(index, path+",NO");
		}
		new FileHandler().updateData(frame.fileData);
		frame.body.removeAll();
		frame.getAllFiles();
		
	}

}
