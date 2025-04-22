    private AdaptingSaveableView saveableView;
    private ISecondarySaveableSource dirtyDisallowed;
    private ISecondarySaveableSource dirtyAllowed;
    private MockAdapterFactory adapterFactory;

    static class MockAdapterFactory implements IAdapterFactory {

        private Map<Class<?>, Object> adaptersMap;

        public MockAdapterFactory() {
            adaptersMap = new HashMap<>();
        }

        @Override
        public Class<?>[] getAdapterList() {
            return adaptersMap.keySet().toArray(new Class[0]);
        }

        public <T> void setAdapter(Class<T> clazz, T adapter) {
            adaptersMap.put(clazz, adapter);
        }

        @Override
        public <T> T getAdapter(Object adaptableObject, Class<T> adapterType) {
            return adapterType.cast(adaptersMap.get(adapterType));
        }
    }

    public DirtyStatePropertySheetTest(String testName) {
        super(testName);
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
