    /**
     *
     */
    private static final String ACTION_SET_ID = "org.eclipse.newActionSet1.newActionSet1";
    private static final String PART_ID = "org.eclipse.ui.tests.part1";

    public ActionSetTests(String testName) {
        super(testName);
    }

    public void testActionSets() throws Exception {
        WorkbenchWindow window = (WorkbenchWindow) openTestWindow();
        boolean [] found = new boolean[] {false};
        WWinPluginAction [] action = new WWinPluginAction[1];

        assertNull(window.getActionBars().getMenuManager().findUsingPath("menu1"));
        assertNull(getActionSetRegistry().findActionSet(ACTION_SET_ID));
        findInPresentation(window, action, found);
        assertFalse("Action set found", found[0]);
        assertNull("Action found", action[0]);

        action[0] = null;
        found[0] = false;
        getBundle();

        assertNotNull(window.getActionBars().getMenuManager().findUsingPath("menu1"));
        assertNotNull(getActionSetRegistry().findActionSet(ACTION_SET_ID));
        findInPresentation(window, action, found);
        assertTrue("Action set not found", found[0]);
        assertNotNull("Action not found", action[0]);
