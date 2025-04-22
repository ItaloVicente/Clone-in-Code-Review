	private EditorRegistry editorRegistry;

	protected void addEditors(EditorRegistry registry) {
		IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
		this.editorRegistry = registry;
		readRegistry(extensionRegistry, PlatformUI.PLUGIN_ID, IWorkbenchRegistryConstants.PL_EDITOR);
	}

	@Override
