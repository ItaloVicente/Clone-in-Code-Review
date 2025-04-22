		List<Ref> allRefs;
		try {
			allRefs = getRefDatabase().getRefs();
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
