		DiffEntry r;
		try {
			r = findRename(parent, n.sourceCommit, n.sourcePath);
			if (r == null) {
				return result(n);
			}
		} catch (CanceledException e) {
