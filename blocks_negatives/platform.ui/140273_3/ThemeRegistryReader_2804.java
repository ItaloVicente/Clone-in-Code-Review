    /**
     * The translation bundle in which to look up internationalized text.
     */
    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
            .getBundle(ThemeRegistryReader.class.getName());

    private Collection categoryDefinitions = new HashSet();

    private Collection colorDefinitions = new HashSet();

    private Collection fontDefinitions = new HashSet();

    private ThemeDescriptor themeDescriptor = null;

    private ThemeRegistry themeRegistry;

    private Map dataMap = new HashMap();

    /**
     * ThemeRegistryReader constructor comment.
     */
    public ThemeRegistryReader() {
        super();
    }

    /**
     * Returns the category definitions.
     *
     * @return the category definitions
     */
    public Collection getCategoryDefinitions() {
        return categoryDefinitions;
    }

    /**
     * Returns the color definitions.
     *
     * @return the color definitions
     */
    public Collection getColorDefinitions() {
        return colorDefinitions;
    }

    /**
     * Returns the data map.
     *
     * @return the data map
     */
    public Map getData() {
        return dataMap;
    }

    /**
     * Returns the font definitions.
     *
     * @return the font definitions
     */
    public Collection getFontDefinitions() {
        return fontDefinitions;
    }

    /**
     * Read a category.
     *
     * @param element the element to read
     * @return the new category
     */
    private ThemeElementCategory readCategory(IConfigurationElement element) {
        String name = element.getAttribute(IWorkbenchRegistryConstants.ATT_LABEL);

        String id = element.getAttribute(IWorkbenchRegistryConstants.ATT_ID);
        String parentId = element.getAttribute(IWorkbenchRegistryConstants.ATT_PARENT_ID);

        String description = null;

        IConfigurationElement[] descriptions = element
                .getChildren(IWorkbenchRegistryConstants.TAG_DESCRIPTION);

        if (descriptions.length > 0) {
