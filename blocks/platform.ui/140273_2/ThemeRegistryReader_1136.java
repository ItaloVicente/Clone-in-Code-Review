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
