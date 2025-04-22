	private AlternateHandle[] loadAlternates() throws IOException {
		final List<AlternateHandle> l = new ArrayList<AlternateHandle>(4);
		final BufferedReader br = open(alternatesFile);
		try {
			String line;
			while ((line = br.readLine()) != null) {
				l.add(openAlternate(line));
			}
		} finally {
			br.close();
