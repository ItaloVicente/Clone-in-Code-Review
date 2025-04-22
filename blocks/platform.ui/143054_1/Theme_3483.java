	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(Theme.class.getName());

	private CascadingColorRegistry themeColorRegistry;

	private CascadingFontRegistry themeFontRegistry;

	private IThemeDescriptor descriptor;

	private IPropertyChangeListener themeListener;

	private CascadingMap dataMap;

	private ThemeRegistry themeRegistry;

	private IPropertyChangeListener propertyListener;

	public Theme(IThemeDescriptor descriptor) {
		themeRegistry = ((ThemeRegistry) WorkbenchPlugin.getDefault().getThemeRegistry());
		this.descriptor = descriptor;
		IWorkbench workbench = PlatformUI.getWorkbench();
		if (descriptor != null) {
			ITheme defaultTheme = workbench.getThemeManager().getTheme(IThemeManager.DEFAULT_THEME);

			ColorDefinition[] colorDefinitions = this.descriptor.getColors();
			themeColorRegistry = new CascadingColorRegistry(defaultTheme.getColorRegistry());
			if (colorDefinitions.length > 0) {
				ThemeElementHelper.populateRegistry(this, colorDefinitions, PrefUtil.getInternalPreferenceStore());
			}

			FontDefinition[] fontDefinitions = this.descriptor.getFonts();
			themeFontRegistry = new CascadingFontRegistry(defaultTheme.getFontRegistry());
			if (fontDefinitions.length > 0) {
				ThemeElementHelper.populateRegistry(this, fontDefinitions, PrefUtil.getInternalPreferenceStore());
			}

			dataMap = new CascadingMap(((ThemeRegistry) WorkbenchPlugin.getDefault().getThemeRegistry()).getData(),
					descriptor.getData());
		}

		getColorRegistry().addListener(getCascadeListener());
		getFontRegistry().addListener(getCascadeListener());
		PrefUtil.getInternalPreferenceStore().addPropertyChangeListener(getPropertyListener());
	}

	private IPropertyChangeListener getPropertyListener() {
		if (propertyListener == null) {
			propertyListener = new IPropertyChangeListener() {

				@Override
