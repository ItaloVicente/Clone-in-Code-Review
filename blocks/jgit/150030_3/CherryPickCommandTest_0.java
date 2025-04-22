	@Test
	public void testCherryPickConflictFiresModifiedEvent() throws Exception {
		ListenerHandle listener = null;
		try (Git git = new Git(db)) {
			RevCommit sideCommit = prepareCherryPick(git);
			ChangeRecorder recorder = new ChangeRecorder();
			listener = db.getListenerList()
					.addWorkingTreeModifiedListener(recorder);
			CherryPickResult result = git.cherryPick()
					.include(sideCommit.getId()).call();
			assertEquals(CherryPickStatus.CONFLICTING
			recorder.assertEvent(new String[] { "a" }
		} finally {
			if (listener != null) {
				listener.remove();
			}
		}
	}

