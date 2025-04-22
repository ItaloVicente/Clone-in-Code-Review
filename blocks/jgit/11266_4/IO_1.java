	public static List<String> readLines(final BufferedReader in)
			throws IOException {
		List<String> l = new ArrayList<String>();
		String s = null;
		while ((s = in.readLine()) != null) {
			l.add(s);
		}
		return l;
	}

