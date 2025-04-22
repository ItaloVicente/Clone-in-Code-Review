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
