	@Test
	public void testCherryPickConflictFiresModifiedEvent() throws Exception {
		try (Git git = new Git(db)) {
			RevCommit sideCommit = prepareCherryPick(git);
			ChangeRecorder recorder = new ChangeRecorder();
			db.getListenerList().addWorkingTreeModifiedListener(recorder);
			CherryPickResult result = git.cherryPick()
					.include(sideCommit.getId()).call();
			assertEquals(CherryPickStatus.CONFLICTING
			recorder.assertEvent(new String[] { "a" }
		}
	}

