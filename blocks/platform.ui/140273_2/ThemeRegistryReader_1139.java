		return element.getAttribute(IWorkbenchRegistryConstants.ATT_VALUE);
	}

	private String checkColorFactory(IConfigurationElement element) {
		String value = null;
		if (element.getAttribute(IWorkbenchRegistryConstants.ATT_COLORFACTORY) != null
				|| element.getChildren(IWorkbenchRegistryConstants.ATT_COLORFACTORY).length > 0) {
			try {
				IColorFactory factory = (IColorFactory) element
						.createExecutableExtension(IWorkbenchRegistryConstants.ATT_COLORFACTORY);
				value = StringConverter.asString(factory.createColor());
			} catch (Exception e) {
				WorkbenchPlugin.log(RESOURCE_BUNDLE.getString("Colors.badFactory"), //$NON-NLS-1$
						WorkbenchPlugin.getStatus(e));
			}
		}
		return value;
	}

	protected ThemeDescriptor readTheme(IConfigurationElement element) {
		ThemeDescriptor desc = null;

		String id = element.getAttribute(ThemeDescriptor.ATT_ID);
		if (id == null) {
