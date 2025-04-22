		super.doSetUp();
		fWindow = openTestWindow();
		fPage = fWindow.getActivePage();
	}

	public void testActionEnablementWhenActive() throws Throwable {
		MockEditorPart editor = openEditor(fPage, "1");
		MockEditorActionBarContributor contributor = (MockEditorActionBarContributor) editor.getEditorSite()
				.getActionBarContributor();

		contributor.enableActions(true);
		verifyToolItemState(contributor, true);

		contributor.enableActions(false);
		verifyToolItemState(contributor, false);
	}

	public void testActionEnablementWhenInactive() throws Throwable {
		MockEditorPart editor = openEditor(fPage, "2");
		MockEditorActionBarContributor contributor = (MockEditorActionBarContributor) editor.getEditorSite()
				.getActionBarContributor();

		contributor.enableActions(true);
		verifyToolItemState(contributor, true);

		fPage.showView(MockViewPart.ID);
		contributor.enableActions(false);
		fPage.activate(editor);
		verifyToolItemState(contributor, false);

		fPage.showView(MockViewPart.ID);
		contributor.enableActions(true);
		fPage.activate(editor);
		verifyToolItemState(contributor, true);
	}

	public void testCoolBarContribution() throws Throwable {

		MockEditorPart editor = openEditor(fPage, "3");
		MockEditorActionBarContributor contributor = (MockEditorActionBarContributor) editor.getEditorSite()
				.getActionBarContributor();

		assertTrue(contributor.getActionBars() instanceof IActionBars2);
		IActionBars2 actionBars = (IActionBars2) contributor.getActionBars();

		assertTrue(actionBars.getCoolBarManager() instanceof SubCoolBarManager);
		SubCoolBarManager coolBarManager = (SubCoolBarManager) actionBars.getCoolBarManager();
		assertTrue("Coolbar should be visible", coolBarManager.isVisible());
	}

	protected MockEditorPart openEditor(IWorkbenchPage page, String suffix) throws Throwable {
		IProject proj = FileUtil.createProject("IEditorActionBarsTest");
		IFile file = FileUtil.createFile("test" + suffix + ".txt", proj);
		return (MockEditorPart) page.openEditor(new FileEditorInput(file), EDITOR_ID);
	}

	protected void verifyToolItemState(MockEditorActionBarContributor ctr, boolean enabled) {
		MockAction[] actions = ctr.getActions();
		for (MockAction action : actions) {
