        super.doSetUp();
        fWindow = openTestWindow();
        fPage = fWindow.getActivePage();
    }

    public void testGetMenuManager() throws Throwable {

        IViewPart part = fPage.showView(MockViewPart.ID);
        IActionBars bars = part.getViewSite().getActionBars();
        assertNotNull(bars);
        IMenuManager mgr = bars.getMenuManager();
        assertNotNull(mgr);
    }

    public void testGetStatusLineManager() throws Throwable {

        IViewPart part = fPage.showView(MockViewPart.ID);
        IActionBars bars = part.getViewSite().getActionBars();
        assertNotNull(bars);
        IStatusLineManager mgr = bars.getStatusLineManager();
        assertNotNull(mgr);
    }

    public void testGetToolBarManager() throws Throwable {

        IViewPart part = fPage.showView(MockViewPart.ID);
        IActionBars bars = part.getViewSite().getActionBars();
        assertNotNull(bars);
        IToolBarManager mgr = bars.getToolBarManager();
        assertNotNull(mgr);
    }

    public void testGetGlobalActionHandler() throws Throwable {

        IViewPart part = fPage.showView(MockViewPart.ID);
        IActionBars bars = part.getViewSite().getActionBars();
        assertNotNull(bars);

        assertNull(bars.getGlobalActionHandler(IWorkbenchActionConstants.CUT));
        assertNull(bars.getGlobalActionHandler(IWorkbenchActionConstants.COPY));
        assertNull(bars.getGlobalActionHandler(IWorkbenchActionConstants.UNDO));

        MockAction cut = new MockAction();
        MockAction copy = new MockAction();
        MockAction undo = new MockAction();

        bars.setGlobalActionHandler(IWorkbenchActionConstants.CUT, cut);
        bars.setGlobalActionHandler(IWorkbenchActionConstants.COPY, copy);
        bars.setGlobalActionHandler(IWorkbenchActionConstants.UNDO, undo);
        bars.updateActionBars();

        assertEquals(cut, bars
                .getGlobalActionHandler(IWorkbenchActionConstants.CUT));
        assertEquals(copy, bars
                .getGlobalActionHandler(IWorkbenchActionConstants.COPY));
        assertEquals(undo, bars
                .getGlobalActionHandler(IWorkbenchActionConstants.UNDO));
    }

        public void testSetGlobalActionHandler() throws Throwable {

	        IViewPart part = fPage.showView(MockViewPart.ID);
	        IActionBars bars = part.getViewSite().getActionBars();
	        assertNotNull(bars);

	        MockAction cut = new MockAction();
	        MockAction copy = new MockAction();
	        MockAction undo = new MockAction();

	        bars.setGlobalActionHandler(IWorkbenchActionConstants.CUT, cut);
	        bars.setGlobalActionHandler(IWorkbenchActionConstants.COPY, copy);
	        bars.setGlobalActionHandler(IWorkbenchActionConstants.UNDO, undo);
	        bars.updateActionBars();

	        cut.hasRun = copy.hasRun = undo.hasRun = false;

	        runMatchingCommand(fWindow, ActionFactory.CUT.getId());

	        ActionUtil.runActionUsingPath(this, fWindow,
	                IWorkbenchActionConstants.M_EDIT + '/'
	                        + IWorkbenchActionConstants.UNDO);
	        assertTrue(cut.hasRun);
	        assertTrue(!copy.hasRun);
	        assertTrue(undo.hasRun);

	        fPage.showView(MockViewPart.ID2);
	        cut.hasRun = copy.hasRun = undo.hasRun = false;
	        runMatchingCommand(fWindow, ActionFactory.CUT.getId());
	        ActionUtil.runActionUsingPath(this, fWindow,
	                IWorkbenchActionConstants.M_EDIT + '/'
	                        + IWorkbenchActionConstants.UNDO);
	        assertTrue(!cut.hasRun);
	        assertTrue(!copy.hasRun);
	        assertTrue(!undo.hasRun);

	        fPage.activate(part);
	        cut.hasRun = copy.hasRun = undo.hasRun = false;
	        runMatchingCommand(fWindow, ActionFactory.CUT.getId());
	        ActionUtil.runActionUsingPath(this, fWindow,
	                IWorkbenchActionConstants.M_EDIT + '/'
	                        + IWorkbenchActionConstants.UNDO);
	        assertTrue(cut.hasRun);
	        assertTrue(!copy.hasRun);
	        assertTrue(undo.hasRun);
	    }

    private void runMatchingCommand(IWorkbenchWindow window, String actionId) {
    	IHandlerService hs = window.getService(IHandlerService.class);
    	IActionCommandMappingService ms = window.getService(IActionCommandMappingService.class);
    	String commandId = ms.getCommandId(actionId);
    	assertNotNull(commandId);
    	try {
