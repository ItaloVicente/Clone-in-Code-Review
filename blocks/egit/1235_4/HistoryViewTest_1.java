
	@Test
	public void testAddBranch() throws Exception {
		Repository repo = lookupRepository(repoFile);
		assertNull(repo.resolve(Constants.R_HEADS + "NewBranch"));
		SWTBotTable table = getHistoryViewTable(PROJ1);
		table.getTableItem(0).select();

		ContextMenuHelper.clickContextMenu(table, util
				.getPluginLocalizedValue("CreateBranchOnCommitActionLabel"));
		SWTBotShell dialog = bot
				.shell(UIText.BranchSelectionDialog_QuestionNewBranchTitle);
		dialog.bot().text().setText("NewBranch");
		dialog.bot().button(IDialogConstants.OK_LABEL).click();
		waitInUI();
		assertNotNull(repo.resolve(Constants.R_HEADS + "NewBranch"));
	}

	@Test
	public void testAddTag() throws Exception {
		Repository repo = lookupRepository(repoFile);
		assertNull(repo.resolve(Constants.R_TAGS + "NewTag"));
		final SWTBotTable table = getHistoryViewTable(PROJ1);
		table.getTableItem(0).select();
		final RevCommit[] commit = new RevCommit[1];

		Display.getDefault().syncExec(new Runnable() {

			public void run() {
				commit[0] = (RevCommit) table.widget.getSelection()[0]
						.getData();
			}
		});

		ContextMenuHelper.clickContextMenu(table, util
				.getPluginLocalizedValue("CreateTagOnCommitActionLabel"));
		SWTBotShell dialog = bot.shell(NLS.bind(
				UIText.CreateTagDialog_CreateTagOnCommitTitle, commit[0]
						.getId().name()));
		dialog.bot().textWithLabel(UIText.CreateTagDialog_tagName).setText(
				"NewTag");
		dialog.bot().textWithLabel(UIText.CreateTagDialog_tagMessage).setText(
				"New Tag message");
		dialog.bot().button(IDialogConstants.OK_LABEL).click();
		waitInUI();
		assertNotNull(repo.resolve(Constants.R_TAGS + "NewTag"));
	}

	@Test
	public void testCheckOut() throws Exception {
		Repository repo = lookupRepository(repoFile);
		assertEquals(Constants.MASTER, repo.getBranch());

		final SWTBotTable table = getHistoryViewTable(PROJ1);
		table.getTableItem(1).select();
		final RevCommit[] commit = new RevCommit[1];

		Display.getDefault().syncExec(new Runnable() {

			public void run() {
				commit[0] = (RevCommit) table.widget.getSelection()[0]
						.getData();
			}
		});

		ContextMenuHelper.clickContextMenu(table, util
				.getPluginLocalizedValue("CheckoutCommand"));

		waitInUI();
		assertEquals(commit[0].getId().name(), repo.getBranch());
	}
