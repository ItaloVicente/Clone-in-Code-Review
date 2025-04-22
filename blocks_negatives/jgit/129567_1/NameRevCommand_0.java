			for (String prefix : prefixes)
				addPrefix(prefix, nonCommits, pending);
		} else if (refs == null)
			addPrefix(Constants.R_REFS, nonCommits, pending);
	}

	private void addPrefix(String prefix, Map<ObjectId, String> nonCommits,
			FIFORevQueue pending) throws IOException {
		for (Ref ref : repo.getRefDatabase().getRefsByPrefix(prefix))
			addRef(ref, nonCommits, pending);
