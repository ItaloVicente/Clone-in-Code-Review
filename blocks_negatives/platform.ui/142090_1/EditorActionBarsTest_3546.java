        super.doSetUp();
        fWindow = openTestWindow();
        fPage = fWindow.getActivePage();
    }

    /**
     * Test action enablement / disablement when a
     * part is active.
     * <p>
     * Created for PR 1GJNB52: ToolItems in EditorToolBarManager can get
     * out of synch with the state of the IAction
     * </p>
     */
    public void testActionEnablementWhenActive() throws Throwable {
        MockEditorPart editor = openEditor(fPage, "1");
        MockEditorActionBarContributor contributor = (MockEditorActionBarContributor) editor
                .getEditorSite().getActionBarContributor();

        contributor.enableActions(true);
        verifyToolItemState(contributor, true);

        contributor.enableActions(false);
        verifyToolItemState(contributor, false);
    }

    /**
     * Test action enablement / disablement when a
     * part is inactive.
     * <p>
     * Created for PR 1GJNB52: ToolItems in EditorToolBarManager can get
     * out of synch with the state of the IAction
     * </p>
     */
    public void testActionEnablementWhenInactive() throws Throwable {
        MockEditorPart editor = openEditor(fPage, "2");
        MockEditorActionBarContributor contributor = (MockEditorActionBarContributor) editor
                .getEditorSite().getActionBarContributor();

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
        MockEditorActionBarContributor contributor = (MockEditorActionBarContributor) editor
                .getEditorSite().getActionBarContributor();

        assertTrue(contributor.getActionBars() instanceof IActionBars2);
        IActionBars2 actionBars = (IActionBars2) contributor.getActionBars();

        assertTrue(actionBars.getCoolBarManager() instanceof SubCoolBarManager);
        SubCoolBarManager coolBarManager = (SubCoolBarManager) actionBars.getCoolBarManager();
        assertTrue("Coolbar should be visible", coolBarManager.isVisible());
    }



    /**
     * Open a test editor.
     */
    protected MockEditorPart openEditor(IWorkbenchPage page, String suffix)
            throws Throwable {
        IProject proj = FileUtil.createProject("IEditorActionBarsTest");
        IFile file = FileUtil.createFile("test" + suffix + ".txt", proj);
        return (MockEditorPart) page.openEditor(new FileEditorInput(file),
                EDITOR_ID);
    }

    /**
     * Tests whether actions are enabled.
     */
    protected void verifyToolItemState(MockEditorActionBarContributor ctr,
            boolean enabled) {
        MockAction[] actions = ctr.getActions();
        for (MockAction action : actions) {
