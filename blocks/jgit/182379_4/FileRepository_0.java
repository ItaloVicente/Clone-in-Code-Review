	private List<Ref> getAllRefs(Repository repo) {
		try {
			return repo.getRefDatabase().getRefs();
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

