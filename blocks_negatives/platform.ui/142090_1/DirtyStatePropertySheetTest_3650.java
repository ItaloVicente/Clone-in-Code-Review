    }

    @Override
    protected void doSetUp() throws Exception {
        super.doSetUp();
        PropertySheetPerspectiveFactory.applyPerspective(activePage);
        dirtyDisallowed = new ISecondarySaveableSource() {
        };
        dirtyAllowed = new ISecondarySaveableSource() {
            @Override
            public boolean isDirtyStateSupported() {
                return true;
            }
        };
        adapterFactory = new MockAdapterFactory();
        propertySheet = (PropertySheet) activePage.showView(IPageLayout.ID_PROP_SHEET);
