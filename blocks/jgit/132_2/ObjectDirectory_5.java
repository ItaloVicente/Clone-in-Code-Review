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
				}
			} finally {
				br.close();
