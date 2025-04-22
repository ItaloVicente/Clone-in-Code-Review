        viewer = new TreeViewer(shell);
        contentProvider = new RefreshTestTreeContentProvider();
        viewer.setContentProvider(contentProvider);
        viewer.setLabelProvider(getLabelProvider());
        return viewer;
    }

    /**
     * Test the time for doing a refresh.
     * @throws Throwable
     */
    public void testRefresh() throws Throwable {
        openBrowser();
