        super.doSetUp();
        fWindow = openTestWindow();
        fPage = fWindow.getActivePage();
    }

    public void testGetId() throws Throwable {

        IWorkbenchPart part = createTestPart(fPage);
        IWorkbenchPartSite site = part.getSite();
        assertEquals(getTestPartId(), site.getId());
    }

    public void testGetPage() throws Throwable {

        IWorkbenchPart part = createTestPart(fPage);
        IWorkbenchPartSite site = part.getSite();
        assertEquals(fPage, site.getPage());
    }

    public void testGetPluginId() throws Throwable {

        IWorkbenchPart part = createTestPart(fPage);
        IWorkbenchPartSite site = part.getSite();
        assertEquals(getTestPartPluginId(), site.getPluginId());
    }

    public void testGetRegisteredName() throws Throwable {

        IWorkbenchPart part = createTestPart(fPage);
        IWorkbenchPartSite site = part.getSite();
        assertEquals(getTestPartName(), site.getRegisteredName());
    }

    public void testGetShell() throws Throwable {

        IWorkbenchPart part = createTestPart(fPage);
        IWorkbenchPartSite site = part.getSite();
        assertEquals(fWindow.getShell(), site.getShell());
    }

    public void testGetWorkbenchWindow() throws Throwable {

        IWorkbenchPart part = createTestPart(fPage);
        IWorkbenchPartSite site = part.getSite();
        assertEquals(fWindow, site.getWorkbenchWindow());
    }

    public void testGetSelectionProvider() throws Throwable {

        IWorkbenchPart part = createTestPart(fPage);
        IWorkbenchPartSite site = part.getSite();
        assertTrue(part instanceof MockWorkbenchPart);
        MockWorkbenchPart mock = (MockWorkbenchPart) part;
        assertEquals(mock.getSelectionProvider(), site.getSelectionProvider());
    }

    public void testSetSelectionProvider() throws Throwable {

        IWorkbenchPart part = createTestPart(fPage);
        IWorkbenchPartSite site = part.getSite();
        site.setSelectionProvider(null);
        assertNull(site.getSelectionProvider());

        MockSelectionProvider provider = new MockSelectionProvider();
        site.setSelectionProvider(provider);
        assertEquals(provider, site.getSelectionProvider());
    }

    public void testINestableService() throws Throwable {
    	IWorkbenchPart part = createTestPart(fPage);
    	IWorkbenchPartSite site = part.getSite();
