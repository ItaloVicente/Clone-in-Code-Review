    private List themes;

    private List colors;

    private List fonts;

    private List categories;

    private Map dataMap;

    /**
     * Map from String (categoryId) -> Set (presentationIds)
     */
    private Map categoryBindingMap;

    /**
     * Create a new ThemeRegistry.
     */
    public ThemeRegistry() {
        themes = new ArrayList();
        colors = new ArrayList();
        fonts = new ArrayList();
        categories = new ArrayList();
        dataMap = new HashMap();
        categoryBindingMap = new HashMap();
    }

    /**
     * Add a descriptor to the registry.
     */
    void add(IThemeDescriptor desc) {
        if (findTheme(desc.getId()) != null) {
