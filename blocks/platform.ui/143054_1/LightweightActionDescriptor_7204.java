public class LightweightActionDescriptor implements IAdaptable, IWorkbenchAdapter {
	private static final Object[] NO_CHILDREN = new Object[0];

	private String id;

	private String label;

	private String description;

	private ImageDescriptor image;

	public LightweightActionDescriptor(IConfigurationElement actionElement) {
		super();

		this.id = actionElement.getAttribute(IWorkbenchRegistryConstants.ATT_ID);
		this.label = actionElement.getAttribute(IWorkbenchRegistryConstants.ATT_LABEL);
		this.description = actionElement.getAttribute(IWorkbenchRegistryConstants.TAG_DESCRIPTION);

		String iconName = actionElement.getAttribute(IWorkbenchRegistryConstants.ATT_ICON);
		if (iconName != null) {
			IExtension extension = actionElement.getDeclaringExtension();
			this.image = AbstractUIPlugin.imageDescriptorFromPlugin(extension.getContributor().getName(), iconName);
		}
	}

