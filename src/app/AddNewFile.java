package app;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class AddNewFile implements ActionListener {

	FileEncryptionFrame frame;

	AddNewFile(FileEncryptionFrame frame) {
		this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		// TODO Auto-generated method stub
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setMultiSelectionEnabled(true);
		fileChooser.showOpenDialog(frame);
		File[] files=fileChooser.getSelectedFiles();
		
		for(int i=0; i<files.length; i++) {
			
			String path = files[i].getAbsolutePath();
			if (frame.fileData.contains(path + ",NO") || frame.fileData.contains(path + ",YES")) {
				JOptionPane.showMessageDialog(null, "\""+files[i].getAbsolutePath()+"\" File already added..!");
				continue;
			}
			path += ",NO";
			frame.fileData.add(path);

			frame.body.add(frame.getPanel(path, (frame.fileData.size() - 1)));
			frame.setSize(frame.getWidth(),
					(frame.fileData.size() == 1 ? FileEncryptionFrame.PANEL_HEIGHT + 40 : frame.getHeight())
							+ FileEncryptionFrame.PANEL_HEIGHT);
		}
		

	}

}