
	@Test
	public void showAnnotations() throws Exception {
		final AtomicReference<IEditorPart> editorRef = new AtomicReference<IEditorPart>();
		PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {

			public void run() {
				RepositoryCommit repoCommit = new RepositoryCommit(repository,
						commit);
				editorRef.set(CommitEditor.openQuiet(repoCommit));
			}
		});
		assertNotNull(editorRef.get());
		SWTBotEditor commitEditor = bot.activeEditor();
		SWTBotTable table = commitEditor.bot().table(0);
		assertTrue(table.rowCount() > 0);
		table.select(0);
		ContextMenuHelper.clickContextMenu(table,
				UIText.CommitFileDiffViewer_ShowAnnotationsMenuLabel);
		TestUtil.joinJobs(JobFamilies.BLAME);
		assertFalse(commitEditor.getReference().equals(
				bot.activeEditor().getReference()));

		final String content = getTestFileContent();
		setTestFileContent("updated content" + System.nanoTime());
		assertEquals(content, bot.activeEditor().toTextEditor().getText());
	}
