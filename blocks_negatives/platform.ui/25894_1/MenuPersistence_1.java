	private void readEditorActions() {
		final IExtensionRegistry registry = Platform.getExtensionRegistry();
		ArrayList<IConfigurationElement> configElements = new ArrayList<IConfigurationElement>();

		final IConfigurationElement[] extElements = registry
				.getConfigurationElementsFor(IWorkbenchRegistryConstants.EXTENSION_EDITOR_ACTIONS);
		for (IConfigurationElement element : extElements) {
			if (contributorFilter == null
					|| contributorFilter.matcher(element.getContributor().getName()).matches()) {
				configElements.add(element);
			}
		}

		Collections.sort(configElements, comparer);

		for (IConfigurationElement element : configElements) {
			for (IConfigurationElement child : element.getChildren()) {
				if (child.getName().equals(IWorkbenchRegistryConstants.TAG_ACTION)) {
					EditorAction editorAction = new EditorAction(application, appContext, element,
							child);
					editorActionContributions.add(editorAction);
					editorAction.addToModel(menuContributions, toolBarContributions,
							trimContributions);
				}
			}
		}
	}

