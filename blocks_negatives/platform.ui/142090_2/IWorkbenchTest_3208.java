    public IWorkbenchTest(String testName) {
        super(testName);
    }

    /**
     * Tests the activation of two windows.
     */
    public void XXXtestGetActiveWorkbenchWindow() throws Throwable {
        IWorkbenchWindow win1, win2;

        win1 = fWorkbench.getActiveWorkbenchWindow();
        assertNotNull(win1);

        win1 = openTestWindow();
        assertEquals(win1, fWorkbench.getActiveWorkbenchWindow());

        win2 = openTestWindow();
        assertEquals(win2, fWorkbench.getActiveWorkbenchWindow());

        win1.getShell().forceFocus();
        processEvents();
        assertEquals(win1, fWorkbench.getActiveWorkbenchWindow());

        win2.getShell().forceFocus();
        processEvents();
        assertEquals(win2, fWorkbench.getActiveWorkbenchWindow());

    }

    public void testGetEditorRegistry() throws Throwable {
        IEditorRegistry reg = fWorkbench.getEditorRegistry();
        assertNotNull(reg);
    }

    public void testGetPerspectiveRegistry() throws Throwable {
        IPerspectiveRegistry reg = fWorkbench.getPerspectiveRegistry();
        assertNotNull(reg);
    }

    public void testGetPrefereneManager() throws Throwable {
        PreferenceManager mgr = fWorkbench.getPreferenceManager();
        assertNotNull(mgr);
    }

    public void testGetSharedImages() throws Throwable {
        ISharedImages img = fWorkbench.getSharedImages();
        assertNotNull(img);
    }

    public void testGetWorkingSetManager() throws Throwable {
        IWorkingSetManager workingSetManager = fWorkbench
                .getWorkingSetManager();
        IWorkspace workspace = ResourcesPlugin.getWorkspace();

        assertNotNull(workingSetManager);

        IWorkingSet workingSet = workingSetManager.createWorkingSet("ws1",
                new IAdaptable[] { workspace.getRoot() });
        workingSetManager.addWorkingSet(workingSet);
        workingSetManager = fWorkbench.getWorkingSetManager();
        assertEquals(1, workingSetManager.getWorkingSets().length);
        assertEquals(workingSet, workingSetManager.getWorkingSets()[0]);

        workingSetManager.removeWorkingSet(workingSet);
    }

    public void testGetWorkbenchWindows() throws Throwable {
        IWorkbenchWindow[] wins = fWorkbench.getWorkbenchWindows();
        assertEquals(ArrayUtil.checkNotNull(wins), true);
        int oldTotal = wins.length;
        int num = 3;

        IWorkbenchWindow[] newWins = new IWorkbenchWindow[num];
        for (int i = 0; i < num; i++) {
