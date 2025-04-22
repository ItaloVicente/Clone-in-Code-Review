	@Test
	public void testOpenThisVersionForDeletedFile() throws Exception {
		Git git = Git.wrap(lookupRepository(repoFile));
		git.rm().addFilepattern(FILE1_PATH).call();
		RevCommit commit = git.commit().setMessage("Delete file").call();

		SWTBotTable commitsTable = getHistoryViewTable(PROJ1);
		assertEquals(commitCount + 1, commitsTable.rowCount());
		commitsTable.select(0);

		SWTBot viewBot = getHistoryViewBot();
		SWTBotTable fileDiffTable = viewBot.table(1);
		assertEquals(1, fileDiffTable.rowCount());

		fileDiffTable.select(0);
		fileDiffTable.contextMenu(
				UIText.CommitFileDiffViewer_OpenInEditorMenuLabel).click();

		bot.editorByTitle(FILE1 + " " + commit.getParent(0).getName());
	}

