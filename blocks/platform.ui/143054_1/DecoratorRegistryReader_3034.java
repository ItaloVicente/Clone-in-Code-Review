		values.add(desc);

		return true;

	}

	DecoratorDefinition getDecoratorDefinition(IConfigurationElement element) {

		String id = element.getAttribute(IWorkbenchRegistryConstants.ATT_ID);
		if (ids.contains(id)) {
			logDuplicateId(element);
			return null;
		}
		ids.add(id);

		boolean noClass = element.getAttribute(DecoratorDefinition.ATT_CLASS) == null;

		if (Boolean.parseBoolean(element.getAttribute(IWorkbenchRegistryConstants.ATT_LIGHTWEIGHT)) || noClass) {

			String iconPath = element.getAttribute(LightweightDecoratorDefinition.ATT_ICON);

			if (noClass && iconPath == null) {
				logMissingElement(element, LightweightDecoratorDefinition.ATT_ICON);
				return null;
			}

			return new LightweightDecoratorDefinition(id, element);
		}
		return new FullDecoratorDefinition(id, element);

	}

	Collection readRegistry(IExtensionRegistry in) {
		values.clear();
		ids.clear();
		readRegistry(in, PlatformUI.PLUGIN_ID, IWorkbenchRegistryConstants.PL_DECORATORS);
		return values;
	}

	public Collection getValues() {
		return values;
	}

	protected void logDuplicateId(IConfigurationElement element) {
		logError(element, "Duplicate id found: " + element.getAttribute(IWorkbenchRegistryConstants.ATT_ID));//$NON-NLS-1$
	}
