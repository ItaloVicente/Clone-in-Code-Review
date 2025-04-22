		try {
			IndexDiff diff = new IndexDiff(repo
			diff.diff();
			return new Status(diff);
		} catch (IOException e) {
			throw new JGitInternalException(e.getMessage()
		}
