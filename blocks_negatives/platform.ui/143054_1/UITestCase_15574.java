        addWindowListener();
        doSetUp();

    }

    /**
     * Sets up the fixture, for example, open a network connection.
     * This method is called before a test is executed.
     * The default implementation does nothing.
     * Subclasses may extend.
     */
    protected void doSetUp() throws Exception {
    }

    /**
     * Simple implementation of tearDown.  Subclasses are prevented
     * from overriding this method to maintain logging consistency.
     * doTearDown() should be overriden instead.
     */
    @Override
	protected final void tearDown() throws Exception {
        removeWindowListener();
        doTearDown();
    	fWorkbench = null;
    }

    /**
     * Tears down the fixture, for example, close a network connection.
     * This method is called after a test is executed.
     * The default implementation closes all test windows, processing events both before
     * and after doing so.
     * Subclasses may extend.
     */
    protected void doTearDown() throws Exception {
        processEvents();
        closeAllTestWindows();
        processEvents();
    }
