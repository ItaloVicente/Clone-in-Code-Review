		List<Ref> refs;
		try {
			refs = localDb.getRefDatabase().getRefsByPrefix(RefDatabase.ALL);
		} catch (IOException e) {
			refs = Collections.emptyList();
		}
