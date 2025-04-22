		String elementName = element.getName();
		if (themeDescriptor == null && elementName.equals(IWorkbenchRegistryConstants.TAG_COLORDEFINITION)) {
			ColorDefinition definition = readColor(element);
			if (definition != null) {
				if (!colorDefinitions.contains(definition)) {
					colorDefinitions.add(definition);
					themeRegistry.add(definition);
				}
			}
			return true;
		} else if (themeDescriptor != null && elementName.equals(IWorkbenchRegistryConstants.TAG_COLOROVERRIDE)) {
			ColorDefinition definition = readColor(element);
			if (definition != null) {
				themeDescriptor.add(definition);
			}
			return true;
		} else if (themeDescriptor == null && elementName.equals(IWorkbenchRegistryConstants.TAG_FONTDEFINITION)) {
			FontDefinition definition = readFont(element);
			if (definition != null) {
				if (!fontDefinitions.contains(definition)) {
					fontDefinitions.add(definition);
					themeRegistry.add(definition);
				}
			}
			return true;
		} else if (themeDescriptor != null && elementName.equals(IWorkbenchRegistryConstants.TAG_FONTOVERRIDE)) {
			FontDefinition definition = readFont(element);
			if (definition != null) {
				themeDescriptor.add(definition);
			}
			return true;
		} else if (themeDescriptor == null && elementName.equals(IWorkbenchRegistryConstants.TAG_CATEGORYDEFINITION)) {
			ThemeElementCategory definition = readCategory(element);
			if (definition != null) {
				if (!categoryDefinitions.contains(definition)) {
					categoryDefinitions.add(definition);
					themeRegistry.add(definition);
				}
			}
			return true;
		} else if (element.getName().equals(IWorkbenchRegistryConstants.TAG_THEME)) {
			if (themeDescriptor != null) {
				logError(element, RESOURCE_BUNDLE.getString("Themes.badNesting")); //$NON-NLS-1$
			} else {
				themeDescriptor = readTheme(element);
				if (themeDescriptor != null) {
					readElementChildren(element);
					themeDescriptor = null;
				}
				return true;
			}
		} else if (themeDescriptor != null && elementName.equals(IWorkbenchRegistryConstants.TAG_DESCRIPTION)) {
			themeDescriptor.setDescription(element.getValue());
			return true;
		} else if (elementName.equals(IWorkbenchRegistryConstants.TAG_DATA)) {
			String name = element.getAttribute(IWorkbenchRegistryConstants.ATT_NAME);
			String value = element.getAttribute(IWorkbenchRegistryConstants.ATT_VALUE);
			if (name == null || value == null) {
				logError(element, RESOURCE_BUNDLE.getString("Data.badData")); //$NON-NLS-1$
