	public WorkingSetDescriptor(IConfigurationElement configElement) throws CoreException {
		super();
		this.configElement = configElement;
		id = configElement.getAttribute(ATT_ID);
		name = configElement.getAttribute(ATT_NAME);
		icon = configElement.getAttribute(ATT_ICON);
		pageClassName = configElement.getAttribute(ATT_PAGE_CLASS);
		updaterClassName = configElement.getAttribute(ATT_UPDATER_CLASS);

		if (name == null) {
			throw new CoreException(new Status(IStatus.ERROR, WorkbenchPlugin.PI_WORKBENCH, 0,
					"Invalid extension (missing class name): " + id, //$NON-NLS-1$
					null));
		}
