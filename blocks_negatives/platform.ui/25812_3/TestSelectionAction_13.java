        ISelectionChangedListener {
    public TestSelectionAction(String label, TestBrowser browser) {
        super(label, browser);
        browser.getViewer().addSelectionChangedListener(this);
        setEnabled(false);
    }
