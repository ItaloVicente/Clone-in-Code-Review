	private void assertGitLinkDoesntExist(Merger resolveMerger)
			throws Exception {
		try (TreeWalk tw = new TreeWalk(db)) {
			tw.setRecursive(true);
			tw.reset(resolveMerger.getResultTreeId());

			assertFalse(tw.next());
		}
	}

