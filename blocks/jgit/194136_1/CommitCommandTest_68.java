	@Test
	public void commitMessageVerbatim() throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile("file1"
			git.add().addFilepattern("file1").call();
			RevCommit committed = git.commit().setMessage("#initial commit")
					.call();

			assertEquals("#initial commit"
		}
	}

	@Test
	public void commitMessageStrip() throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile("file1"
			git.add().addFilepattern("file1").call();
			RevCommit committed = git.commit().setMessage(
					"#Comment\ninitial commit\t\n\n commit body \n \t#another comment")
					.setCleanupMode(CleanupMode.STRIP).call();

			assertEquals("initial commit\n\n commit body"
					committed.getFullMessage());
		}
	}

	@Test
	public void commitMessageDefault() throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile("file1"
			git.add().addFilepattern("file1").call();
			RevCommit committed = git.commit().setMessage(
					"#Comment\ninitial commit\t\n\n commit body \n\n\n \t#another comment  ")
					.setCleanupMode(CleanupMode.DEFAULT).call();

			assertEquals("initial commit\n\n commit body"
					committed.getFullMessage());
		}
	}

	@Test
	public void commitMessageDefaultWhitespace() throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile("file1"
			git.add().addFilepattern("file1").call();
			RevCommit committed = git.commit().setMessage(
					"#Comment\ninitial commit\t\n\n commit body \n\n\n \t#another comment  ")
					.setCleanupMode(CleanupMode.DEFAULT).setDefaultClean(false)
					.call();

			assertEquals(
					"#Comment\ninitial commit\n\n commit body\n\n \t#another comment"
					committed.getFullMessage());
		}
	}

