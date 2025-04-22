    /**
     * Creates an instance of <code>Category</code> as a
     * miscellaneous category.
     */
    public Category() {
        this.id = MISC_ID;
        this.name = MISC_NAME;
    }

    /**
     * Creates an instance of <code>Category</code> with
     * an ID and label.
     *
     * @param id the unique identifier for the category
     * @param label the presentation label for this category
     */
    public Category(String id, String label) {
        this.id = id;
        this.name = label;
    }

    /**
     * Creates an instance of <code>Category</code> using the
     * information from the specified configuration element.
     *
     * @param configElement the <code>IConfigurationElement<code> containing
     * 		the ID, label, and optional parent category path.
     * @throws WorkbenchException if the ID or label is <code>null</code
     */
    public Category(IConfigurationElement configElement)
            throws WorkbenchException {
        id = configElement.getAttribute(IWorkbenchRegistryConstants.ATT_ID);

        configurationElement = configElement;
        if (id == null || getLabel() == null) {
