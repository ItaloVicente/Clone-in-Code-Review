	@Test
	public void testCherryPickNewFileFiresModifiedEvent() throws Exception {
		ListenerHandle listener = null;
		try (Git git = new Git(db)) {
			writeTrashFile("test.txt"
			git.add().addFilepattern("test.txt").call();
			git.commit().setMessage("commit1").call();
			git.checkout().setCreateBranch(true).setName("a").call();

			writeTrashFile("side.txt"
			git.add().addFilepattern("side.txt").call();
			RevCommit side = git.commit().setMessage("side").call();
			assertNotNull(side);

			assertNotNull(git.checkout().setName(Constants.MASTER).call());
			writeTrashFile("test.txt"
			assertNotNull(git.add().addFilepattern("test.txt").call());
			assertNotNull(git.commit().setMessage("commit2").call());

			ChangeRecorder recorder = new ChangeRecorder();
			listener = db.getListenerList()
					.addWorkingTreeModifiedListener(recorder);
			CherryPickResult result = git.cherryPick()
					.include(side.getId()).call();
			assertEquals(CherryPickStatus.OK
			recorder.assertEvent(new String[] { "side.txt" }
					ChangeRecorder.EMPTY);
		} finally {
			if (listener != null) {
				listener.remove();
			}
		}
	}

