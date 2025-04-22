    /**
     * @param theme
     * @param property
     * @return
     */
    public static String[] splitPropertyName(Theme theme, String property) {
    	IThemeDescriptor[] descriptors = WorkbenchPlugin.getDefault()
				.getThemeRegistry().getThemes();
