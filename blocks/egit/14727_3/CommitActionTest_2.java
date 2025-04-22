
	@Test
	public void testSortingByName() throws Exception {
		touch(PROJ1, "a", "a");
		touch(PROJ1, "b", "b");
		CommitDialogTester commitDialogTester = CommitDialogTester
				.openCommitDialog(PROJ1);
		commitDialogTester.setShowUntracked(true);
		assertEquals(2, commitDialogTester.getRowCount());
		assertEquals(PROJ1 + "/a", commitDialogTester.getEntryText(0));
		assertEquals(PROJ1 + "/b", commitDialogTester.getEntryText(1));
		commitDialogTester.sortByName();
		commitDialogTester.sortByName();
		assertEquals(PROJ1 + "/b", commitDialogTester.getEntryText(0));
		assertEquals(PROJ1 + "/a", commitDialogTester.getEntryText(1));
		commitDialogTester.cancel();
	}
