		ReflogReader reflogReader = repository.getReflogReader(Constants.HEAD);
		List<ReflogEntry> reflogEntries;
		if (reflogReader == null) {
			return Collections.emptyMap();
		}

		reflogEntries = reflogReader.getReverseEntries();

