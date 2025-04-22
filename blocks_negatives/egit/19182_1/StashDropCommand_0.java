		final int index = node.getIndex();
		if (index < 0)
			return null;
		final RevCommit commit = node.getObject();
		if (commit == null)
			return null;
