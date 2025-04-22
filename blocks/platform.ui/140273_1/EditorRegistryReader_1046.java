		boolean defaultEditor = false;

		if (element.getAttribute(IWorkbenchRegistryConstants.ATT_NAME) == null) {
			logMissingAttribute(element, IWorkbenchRegistryConstants.ATT_NAME);
			return true;
		}

		String extensionsString = element.getAttribute(IWorkbenchRegistryConstants.ATT_EXTENSIONS);
		if (extensionsString != null) {
			StringTokenizer tokenizer = new StringTokenizer(extensionsString, ",");//$NON-NLS-1$
			while (tokenizer.hasMoreTokens()) {
				extensionsVector.add(tokenizer.nextToken().trim());
			}
		}
		String filenamesString = element.getAttribute(IWorkbenchRegistryConstants.ATT_FILENAMES);
		if (filenamesString != null) {
			StringTokenizer tokenizer = new StringTokenizer(filenamesString, ",");//$NON-NLS-1$
			while (tokenizer.hasMoreTokens()) {
				filenamesVector.add(tokenizer.nextToken().trim());
			}
		}

		IConfigurationElement[] bindings = element.getChildren(IWorkbenchRegistryConstants.TAG_CONTENT_TYPE_BINDING);
