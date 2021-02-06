package app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class FileEncryptionFrame extends JFrame {

	/**
	 * 
	 */
	FileHandler fileHandler;
	public Box body;
	public ArrayList<String> fileData;
	private JScrollPane scrollPane;
	final static int PANEL_HEIGHT = 50;
	final static int PANEL_WIDTH = 1000;
	final static Font buttonFont = new Font("Arial", Font.BOLD, 16);
	final static Font textFont = new Font("Arial", Font.PLAIN, 16);
	final static Color DECRYPT_BUTTON_COLOR = Color.orange;
	final static Color ENCRYPT_BUTTON_COLOR = Color.decode("#00FF7F");
	final static Color DELETE_BUTTON_COLOR = Color.decode("#FF2047");
	final static Color ADD_NEW_FILE_BUTTON_COLOR = Color.decode("#00FFFF");

	public FileEncryptionFrame() {

		this.setTitle("File Encryption Application");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fileHandler = new FileHandler();

		
		scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.getVerticalScrollBar().setUnitIncrement(80);
		scrollPane.getVerticalScrollBar().setBackground(Color.decode("#E0FFFF"));
		
		this.setSize(PANEL_WIDTH, 120);
		this.add(scrollPane);
		getAllFiles();

		JPanel bottomPanel = new JPanel();
		bottomPanel.setSize(PANEL_WIDTH, PANEL_HEIGHT);
		JButton addButton = new JButton("Add new file");
		addButton.setBackground(ADD_NEW_FILE_BUTTON_COLOR);
		addButton.setForeground(Color.black);
		addButton.addActionListener(new AddNewFile(this));
		addButton.setFont(buttonFont);
		bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		bottomPanel.add(addButton);
		this.getContentPane().add(bottomPanel, BorderLayout.SOUTH);

	}

	void getAllFiles() {
		
		body = Box.createVerticalBox();
		fileData = fileHandler.getAllFiles();
		for (int index = 0; index < fileData.size(); index++) {
			body.add(getPanel(fileData.get(index), index));
		}
		this.setSize(getWidth(), fileData.size() == 0 ? 500 : (fileData.size() + 1) * PANEL_HEIGHT + 40);
		scrollPane.setViewportView(body);
		repaint();
	}

	public JPanel getPanel(String fileName, int index) {

		boolean isEncrypted = fileName.substring(fileName.lastIndexOf(',') + 1).equals("YES");
		String path = fileName.substring(0, fileName.lastIndexOf(','));
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(new LineBorder(Color.gray, 1));

		JLabel fileNameLabel = new JLabel("File location : " + path);
		fileNameLabel.setIcon(isEncrypted ? new ImageIcon("./resources/lock.png") : null);
		fileNameLabel.setFont(textFont);
		fileNameLabel.setHorizontalTextPosition(JLabel.LEFT);
		panel.add(fileNameLabel, BorderLayout.WEST);

		{
			JPanel buttonPanel = new JPanel(new BorderLayout());
			buttonPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
			buttonPanel.setSize(panel.getSize());
			JButton decryptEncryptButton = new JButton(isEncrypted ? "Decrypt" : "Encrypt");
			decryptEncryptButton.setFont(buttonFont);
			decryptEncryptButton.setBackground(isEncrypted ? DECRYPT_BUTTON_COLOR : ENCRYPT_BUTTON_COLOR);
			decryptEncryptButton.setForeground(Color.black);
			decryptEncryptButton.addActionListener(new FileEncryption(this, index, path, isEncrypted));
			buttonPanel.add(decryptEncryptButton, BorderLayout.WEST);

			JLabel deviderLabel = new JLabel("  ");
			buttonPanel.add(deviderLabel, BorderLayout.CENTER);
			JButton deleteButton = new JButton("Delete");
			deleteButton.setBackground(DELETE_BUTTON_COLOR);
			deleteButton.setFont(buttonFont);
			deleteButton.setEnabled(!isEncrypted);
			deleteButton.addActionListener(e -> {
				fileData.remove(index);
				new FileHandler().updateData(fileData);
				this.body.removeAll();
				this.getAllFiles();
			});
			deleteButton.setForeground(Color.white);
			buttonPanel.add(deleteButton, BorderLayout.EAST);
			panel.add(buttonPanel, BorderLayout.EAST);
		}
		return panel;
	}

	public static void main(String[] args) {
		FileEncryptionFrame appFrame = new FileEncryptionFrame();
		appFrame.setMinimumSize(new Dimension(100, 120));
		appFrame.setLocationRelativeTo(null);
		appFrame.setVisible(true);

	}

}
