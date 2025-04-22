			ReflogReader reflogReader = repository.getReflogReader(
					Constants.HEAD);
			List<ReflogEntry> reflogEntries;
			if (reflogReader == null) {
				reflogEntries = Collections.emptyList();
			} else {
				reflogEntries = reflogReader.getReverseEntries();
			}
