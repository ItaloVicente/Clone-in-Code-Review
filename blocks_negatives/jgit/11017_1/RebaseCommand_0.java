	private void createFile(File parentDir, String name, String content)
			throws IOException {
		File file = new File(parentDir, name);
		FileOutputStream fos = new FileOutputStream(file);
		try {
			fos.write(content.getBytes(Constants.CHARACTER_ENCODING));
			fos.write('\n');
		} finally {
			fos.close();
		}
	}

