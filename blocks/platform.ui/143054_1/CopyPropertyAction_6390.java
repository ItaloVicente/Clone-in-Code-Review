	public CopyPropertyAction(PropertySheetViewer viewer, String name,
			Clipboard clipboard) {
		super(viewer, name);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
				IPropertiesHelpContextIds.COPY_PROPERTY_ACTION);
		this.clipboard = clipboard;
	}
