	public void testCopyPathToClipboard() throws Exception {
		final SWTBotView view = getOrOpenView();
		final SWTBotTreeItem[] items = view.bot().tree().getAllItems();
		items[0].select();
		Display.getDefault().syncExec(new Runnable() {

			public void run() {
				Clipboard clp = new Clipboard(Display.getCurrent());
				clp.clearContents();
				clp.setContents(new Object[] { "x" },
						new TextTransfer[] { TextTransfer.getInstance() });
				String value = (String) clp.getContents(TextTransfer
						.getInstance());
				assertEquals("Clipboard content should be x", "x", value);

				ContextMenuHelper.clickContextMenu(view.bot().tree(), myUtil
						.getPluginLocalizedValue("CopyPathCommand"));

				value = (String) clp.getContents(TextTransfer.getInstance());
				assertTrue("Clipboard content should be a repository path",
						FileKey.isGitRepository(new File(value), FS.DETECTED));

				clp.dispose();
			}
		});

