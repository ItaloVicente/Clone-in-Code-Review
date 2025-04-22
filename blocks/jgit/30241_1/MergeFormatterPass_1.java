
	private void writeln(String s) throws IOException {
		if (!lineBegin)
			out.write('\n');
		lineBegin = true;
	}
