
	private void BUG_WorkAroundRacyGitIssues(String name) {
		File path = new File(db.getDirectory()
		long old = path.lastModified();
		path.setLastModified(set);
		assertTrue("time changed"
	}
