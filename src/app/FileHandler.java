package app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileHandler {

	final static private File dataFile = new File("./filedata");

	ArrayList<String> getAllFiles() {
		if (!dataFile.exists()) {
			try {
				dataFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		ArrayList<String> files = new ArrayList<String>();

		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(dataFile));
			String data = null;

			while ((data = bufferedReader.readLine()) != null) {
				files.add(data);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return files;
	}

	boolean updateData(ArrayList<String> fileData) {

		if (!dataFile.exists()) {
			try {
				dataFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		try {
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(dataFile));

			for (String fileName : fileData) {
				System.out.println(fileName);
				bufferedWriter.write(fileName + "\n");
			}
			bufferedWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
