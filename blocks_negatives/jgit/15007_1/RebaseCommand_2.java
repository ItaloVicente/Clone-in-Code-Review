		BufferedWriter todoWriter = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(todoFile), Constants.CHARACTER_ENCODING));
		try {
			for (String writeLine : todoLines) {
				todoWriter.write(writeLine);
				todoWriter.newLine();
			}
		} finally {
			todoWriter.close();
