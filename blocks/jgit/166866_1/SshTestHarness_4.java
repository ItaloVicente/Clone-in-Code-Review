	private static void write(BufferedWriter out
			throws IOException {
		String data = Base64.getEncoder().encodeToString(bytes);
		int last = data.length();
		for (int i = 0; i < last; i += lineLength) {
			if (i + lineLength <= last) {
				out.write(data.substring(i
			} else {
				out.write(data.substring(i));
			}
			out.newLine();
