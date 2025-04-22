	}

	public void loadNatures() {
		natureMap.clear();
		IExtensionPoint point = Platform.getExtensionRegistry()
				.getExtensionPoint("org.eclipse.core.resources.natures"); //$NON-NLS-1$
