        super.doSetUp();
        fWindow = openTestWindow();
        fPage = fWindow.getActivePage();
    }

    public void testAnyTextAction() throws Throwable {
        ExtendedTextEditor editor = showTextEditor("anyText.exttxt");
        MenuManager mgr = getActionMenuManager(editor);

        selectAndUpdateMenu(editor, "", mgr);
        testAction(mgr, "anyText", true);

        selectAndUpdateMenu(editor, "bob", mgr);
        testAction(mgr, "anyText", true);

        fPage.showView(IPageLayout.ID_BOOKMARKS);
        testAction(mgr, "anyText", false);

        fPage.activate(editor);
        testAction(mgr, "anyText", true);

        selectAndUpdateMenu(editor, "", mgr);
        testAction(mgr, "anyText", true);
    }

    public void testEmptyTextAction() throws Throwable {
        ExtendedTextEditor editor = showTextEditor("emptyText.exttxt");
        MenuManager mgr = getActionMenuManager(editor);

        selectAndUpdateMenu(editor, "", mgr);
        testAction(mgr, "emptyText", true);

        selectAndUpdateMenu(editor, "bob", mgr);
        testAction(mgr, "emptyText", false);

        fPage.showView(IPageLayout.ID_BOOKMARKS);
        testAction(mgr, "emptyText", false);

        fPage.activate(editor);
        testAction(mgr, "emptyText", false);

        selectAndUpdateMenu(editor, "", mgr);
        testAction(mgr, "emptyText", true);
    }

    public void testHelloTextAction() throws Throwable {
        ExtendedTextEditor editor = showTextEditor("helloText.exttxt");
        MenuManager mgr = getActionMenuManager(editor);

        selectAndUpdateMenu(editor, "", mgr);
        testAction(mgr, "helloText", false);

        selectAndUpdateMenu(editor, "bob", mgr);
        testAction(mgr, "helloText", false);

        selectAndUpdateMenu(editor, "Hello", mgr);
        testAction(mgr, "helloText", true);

        fPage.showView(IPageLayout.ID_BOOKMARKS);
        testAction(mgr, "helloText", false);

        fPage.activate(editor);
        testAction(mgr, "helloText", true);

        selectAndUpdateMenu(editor, "bob", mgr);
        testAction(mgr, "helloText", false);
    }

    /**
     * Creates the list view.
     */
    private ExtendedTextEditor showTextEditor(String fileName) throws Throwable {
        IProject proj = FileUtil
                .createProject("TextSelectionActionExpressionTest");
        IFile file = FileUtil.createFile(fileName, proj);
        return (ExtendedTextEditor) IDE.openEditor(fPage, file, true);
    }

    /**
     * Select an object and fire about to show.
     */
    private void selectAndUpdateMenu(ExtendedTextEditor editor, String str,
            MenuManager mgr) throws Throwable {
        editor.setText(str);
        fPage.saveEditor(editor, false);
        ActionUtil.fireAboutToShow(mgr);
    }

    /**
     * Returns the menu manager containing the actions.
     */
    private MenuManager getActionMenuManager(ExtendedTextEditor editor)
            throws Throwable {
        fPage
                .showActionSet("org.eclipse.ui.tests.internal.TextSelectionActions");
        WorkbenchWindow win = (WorkbenchWindow) fWindow;
        IContributionItem item = win.getMenuBarManager().find(
                "org.eclipse.ui.tests.internal.TextSelectionMenu");
        while (item instanceof SubContributionItem) {
            item = ((SubContributionItem) item).getInnerItem();
            if (item instanceof MenuManager) {
