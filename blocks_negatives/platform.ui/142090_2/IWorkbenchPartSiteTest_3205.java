    	assertFalse(service.isActive());

    }


    /**
     * Creates a test part in the page.
     */
    abstract protected IWorkbenchPart createTestPart(IWorkbenchPage page)
            throws Throwable;

    /**
     * Returns the expected id for a test part.
     */
    abstract protected String getTestPartId() throws Throwable;

    /**
     * Returns the expected name for a test part.
     */
    abstract protected String getTestPartName() throws Throwable;

    /**
     * Returns the expected id for a test part plugin.  Subclasses may
     * override this.
     */
    protected String getTestPartPluginId() throws Throwable {
        return "org.eclipse.ui.tests";
    }
