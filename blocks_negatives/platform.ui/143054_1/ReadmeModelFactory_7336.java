    private static ReadmeModelFactory instance = new ReadmeModelFactory();

    private boolean registryLoaded = false;

    IReadmeFileParser parser = null;

    /**
     * Creates a new ReadmeModelFactory.
     */
    private ReadmeModelFactory() {
    }

    /**
     * Adds all mark elements to the list for the subtree rooted
     * at the given mark element.
     */
    protected void addSections(AdaptableList list, MarkElement element) {
        list.add(element);
        Object[] children = element.getChildren(element);
        for (int i = 0; i < children.length; ++i) {
            addSections(list, (MarkElement) children[i]);
        }
    }

    /**
     * Returns the content outline for the given Readme file.
     *
     * @param adaptable  the element for which to return the content outline
     * @return the content outline for the argument
     */
    public AdaptableList getContentOutline(IAdaptable adaptable) {
        return new AdaptableList(getToc((IFile) adaptable));
    }

    /**
     * Returns the singleton readme adapter.
     */
    public static ReadmeModelFactory getInstance() {
        return instance;
    }

    /**
     * Returns a list of all sections in this readme file.
     *
     * @param file  the file for which to return section heading and subheadings
     * @return A list containing headings and subheadings
     */
    public AdaptableList getSections(IFile file) {
        MarkElement[] topLevel = getToc(file);
        AdaptableList list = new AdaptableList();
        for (int i = 0; i < topLevel.length; i++) {
            addSections(list, topLevel[i]);
        }
        return list;
    }

    /**
     * Convenience method.  Looks for a readme file in the selection,
     * and if one is found, returns the sections for it.  Returns null
     * if there is no readme file in the selection.
     */
    public AdaptableList getSections(ISelection sel) {
        if (!(sel instanceof IStructuredSelection))
            return null;
        IStructuredSelection structured = (IStructuredSelection) sel;

        Object object = structured.getFirstElement();
        if (object instanceof IFile) {
            IFile file = (IFile) object;
            String extension = file.getFileExtension();
            if (extension != null
                    && extension.equals(IReadmeConstants.EXTENSION)) {
                return getSections(file);
            }
        }

        return null;
    }

    /**
     * Parses the contents of the Readme file by looking for lines
     * that start with a number.
     *
     * @param file  the file representing the Readme file
     * @return an element collection representing the table of contents
     */
    private MarkElement[] getToc(IFile file) {
        if (registryLoaded == false)
            loadParser();
        return parser.parse(file);
    }

    /**
     * Loads the parser from the registry by searching for
     * extensions that satisfy our published extension point.
     * For the sake of simplicity, we will pick the last extension,
     * allowing tools to override what is used. In a more
     * elaborate tool, all the extensions would be processed.
     */
    private void loadParser() {
        IExtensionPoint point = Platform.getExtensionRegistry().getExtensionPoint(
                IReadmeConstants.PLUGIN_ID, IReadmeConstants.PP_SECTION_PARSER);
        if (point != null) {
            IExtension[] extensions = point.getExtensions();
            for (int i = 0; i < extensions.length; i++) {
                IExtension currentExtension = extensions[i];
                if (i == extensions.length - 1) {
                    IConfigurationElement[] configElements = currentExtension
                            .getConfigurationElements();
                    for (int j = 0; j < configElements.length; j++) {
                        IConfigurationElement config = configElements[i];
                        if (config.getName()
                                .equals(IReadmeConstants.TAG_PARSER)) {
