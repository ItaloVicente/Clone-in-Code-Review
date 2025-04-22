	@Override
	protected ObjectDatabase[] loadAlternates() throws IOException {
		final List<ObjectDatabase> l = new ArrayList<ObjectDatabase>(4);
		if (alternateObjectDir != null) {
			for (File d : alternateObjectDir) {
				l.add(openAlternate(d));
			}
		} else {
			final BufferedReader br = open(alternatesFile);
			try {
				String line;
				while ((line = br.readLine()) != null) {
					l.add(openAlternate(line));
