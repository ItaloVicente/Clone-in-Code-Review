	public void appendFileContent(File file, byte[] content) throws IOException {
		appendFileContent(file, new String(content), true);
	}

	public void appendFileContent(File file, String content) throws IOException {
		appendFileContent(file, new String(content), true);
	}

	public void appendFileContent(File file, byte[] content, boolean append)
			throws IOException {
		appendFileContent(file, new String(content), append);
	}

	public void appendFileContent(File file, String content, boolean append)
			throws IOException {
		FileWriter fw = new FileWriter(file, append);
		fw.append(content);
		fw.close();
	}

