	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(ThemeRegistryReader.class.getName());

	private Collection categoryDefinitions = new HashSet();

	private Collection colorDefinitions = new HashSet();

	private Collection fontDefinitions = new HashSet();

	private ThemeDescriptor themeDescriptor = null;

	private ThemeRegistry themeRegistry;

	private Map dataMap = new HashMap();

	public ThemeRegistryReader() {
		super();
	}

	public Collection getCategoryDefinitions() {
		return categoryDefinitions;
	}

	public Collection getColorDefinitions() {
		return colorDefinitions;
	}

	public Map getData() {
		return dataMap;
	}

	public Collection getFontDefinitions() {
		return fontDefinitions;
	}

	private ThemeElementCategory readCategory(IConfigurationElement element) {
		String name = element.getAttribute(IWorkbenchRegistryConstants.ATT_LABEL);

		String id = element.getAttribute(IWorkbenchRegistryConstants.ATT_ID);
		String parentId = element.getAttribute(IWorkbenchRegistryConstants.ATT_PARENT_ID);

		String description = null;

		IConfigurationElement[] descriptions = element.getChildren(IWorkbenchRegistryConstants.TAG_DESCRIPTION);

		if (descriptions.length > 0) {
