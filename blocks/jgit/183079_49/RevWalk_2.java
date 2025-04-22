	@NonNull
	protected RevCommit lookupCommit(AnyObjectId id
		RevCommit c = (RevCommit) objects.get(id);
		if (c == null) {
			c = createCommitCG(id
			objects.add(c);
		}
		return c;
	}

