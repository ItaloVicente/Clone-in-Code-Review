	@Test
	public void commitWithAutoCrlfAndNonNormalizedIndex() throws Exception {
		try (Git git = new Git(db)) {
			FileBasedConfig config = db.getConfig();
			config.setString("core"
			config.save();
			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			git.commit().setMessage("Initial").call();
			assertEquals(
					"[file.txt
					indexState(CONTENT));
			config.setString("core"
			config.save();
			writeTrashFile("file.txt"
			writeTrashFile("file2.txt"
			git.add().addFilepattern("file.txt").addFilepattern("file2.txt")
					.call();
			git.commit().setMessage("Second").call();
			assertEquals(
					"[file.txt
							+ "[file2.txt
					indexState(CONTENT));
			writeTrashFile("file2.txt"
			git.add().addFilepattern("file2.txt").call();
			git.commit().setMessage("Third").call();
			assertEquals(
					"[file.txt
							+ "[file2.txt
					indexState(CONTENT));
		}
	}

	private void testConflictWithAutoCrlf(String baseLf
			throws Exception {
		try (Git git = new Git(db)) {
			FileBasedConfig config = db.getConfig();
			config.setString("core"
			config.save();
			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			git.commit().setMessage("Initial").call();
			git.checkout().setCreateBranch(true).setName("side").call();
			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			RevCommit side = git.commit().setMessage("Side").call();
			git.checkout().setName("master");
			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			git.commit().setMessage("Second").call();
			config.setString("core"
			config.save();
			CherryPickResult pick = git.cherryPick().include(side).call();
			assertEquals("Expected a cherry-pick conflict"
					CherryPickStatus.CONFLICTING
			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			git.commit().setMessage("Second").call();
			assertEquals("[file.txt
					indexState(CONTENT));
		}
	}

	@Test
	public void commitConflictWithAutoCrlfBaseCrLfOursLf() throws Exception {
		testConflictWithAutoCrlf("\r\n"
	}

	@Test
	public void commitConflictWithAutoCrlfBaseLfOursLf() throws Exception {
		testConflictWithAutoCrlf("\n"
	}

	@Test
	public void commitConflictWithAutoCrlfBasCrLfOursCrLf() throws Exception {
		testConflictWithAutoCrlf("\r\n"
	}

	@Test
	public void commitConflictWithAutoCrlfBaseLfOursCrLf() throws Exception {
		testConflictWithAutoCrlf("\n"
	}

