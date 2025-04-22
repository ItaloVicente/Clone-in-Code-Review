	private boolean contains(List<Ref> refs, RevCommit commit) {
		for (Ref ref : refs)
			if (ref.getObjectId().equals(commit.getId()))
				return true;
		return false;
	}

