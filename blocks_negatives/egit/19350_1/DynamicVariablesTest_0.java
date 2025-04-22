		File f = new File(repository.getWorkTree(), TEST_PROJECT + "/"
				+ TEST_FILE);
		Writer fileWriter = new OutputStreamWriter(new FileOutputStream(f),
				"UTF-8");
		fileWriter.write("Some data");
		fileWriter.close();
		FileWriter fileWriter2 = new FileWriter(new File(repository2.getWorkTree(), TEST_FILE2));
		fileWriter2.write("Some other data");
		fileWriter2.close();
