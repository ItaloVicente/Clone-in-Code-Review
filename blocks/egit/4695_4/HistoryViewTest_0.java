	@Test
	public void testRevertFailure() throws Exception {
		touchAndSubmit(null);
		setTestFileContent("dirty in working directory"
				+ System.currentTimeMillis());
		final SWTBotTable table = getHistoryViewTable(PROJ1);
		assertTrue(table.rowCount() > 0);
		table.getTableItem(0).select();
		final RevCommit[] commit = new RevCommit[1];

		Display.getDefault().syncExec(new Runnable() {

			public void run() {
				TableItem tableItem = table.widget.getSelection()[0];
				ensureTableItemLoaded(tableItem);
				commit[0] = (RevCommit) tableItem.getData();
			}
		});
		assertEquals(1, commit[0].getParentCount());

		ContextMenuHelper.clickContextMenu(table,
				UIText.GitHistoryPage_revertMenuItem);
		SWTBot dialog = bot.shell(UIText.RevertFailureDialog_Title).bot();
		assertEquals(1, dialog.tree().rowCount());
		assertEquals(1, dialog.tree().getAllItems()[0].rowCount());
		assertTrue(dialog.tree().getAllItems()[0].getItems()[0].getText()
				.startsWith(FILE1));
	}

