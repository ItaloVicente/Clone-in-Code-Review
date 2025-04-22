		try {
			return parse(new RevWalk((ObjectReader) null)
		}
		catch (IOException ex) {
			throw new RuntimeException(ex);
		}
