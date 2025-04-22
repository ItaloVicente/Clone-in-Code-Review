
	private String readFile(File directory, String fileName) throws IOException {
		byte[] content = IO.readFully(new File(directory, fileName));
		int end = content.length;
		while (0 < end && content[end - 1] == '\n')
			end--;
		return RawParseUtils.decode(content, 0, end);
	}
