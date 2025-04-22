	@Test
	public void testDeletionConflictWithAutoCrlf() throws Exception {
		try (Git git = new Git(db)) {
			FileBasedConfig config = db.getConfig();
			config.setString("core"
			config.save();
			File file = writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			git.commit().setMessage("Initial").call();
			git.checkout().setCreateBranch(true).setName("side").call();
			assertTrue(file.delete());
			git.rm().addFilepattern("file.txt").call();
			git.commit().setMessage("Side").call();
			config.setString("core"
			config.save();
			git.checkout().setName("master").call();
			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			assertEquals("[file.txt
					indexState(CONTENT));
			writeTrashFile("g"
			git.add().addFilepattern("g/file2.txt");
			RevCommit master = git.commit().setMessage("Second").call();
			git.checkout().setName("side").call();
			CherryPickResult pick = git.cherryPick().include(master).call();
			assertEquals("Expected a cherry-pick conflict"
					CherryPickStatus.CONFLICTING
			git.add().addFilepattern("g/file2.txt").call();
			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			git.commit().setMessage("Cherry").call();
			assertEquals(
					"[file.txt
							+ "[g/file2.txt
					indexState(CONTENT));
		}
	}

