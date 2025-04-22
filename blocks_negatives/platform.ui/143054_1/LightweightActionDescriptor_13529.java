public class LightweightActionDescriptor implements IAdaptable,
        IWorkbenchAdapter {
    private static final Object[] NO_CHILDREN = new Object[0];

    private String id;

    private String label;

    private String description;

    private ImageDescriptor image;

    /**
     * Create a new instance of <code>LightweightActionDescriptor</code>.
     *
     * @param actionElement the configuration element
     */
    public LightweightActionDescriptor(IConfigurationElement actionElement) {
        super();

        this.id = actionElement.getAttribute(IWorkbenchRegistryConstants.ATT_ID);
        this.label = actionElement.getAttribute(IWorkbenchRegistryConstants.ATT_LABEL);
        this.description = actionElement
                .getAttribute(IWorkbenchRegistryConstants.TAG_DESCRIPTION);

        String iconName = actionElement.getAttribute(IWorkbenchRegistryConstants.ATT_ICON);
        if (iconName != null) {
            IExtension extension = actionElement.getDeclaringExtension();
            this.image = AbstractUIPlugin.imageDescriptorFromPlugin(extension
					.getContributor().getName(), iconName);
        }
    }

    /**
     * Returns an object which is an instance of the given class
     * associated with this object. Returns <code>null</code> if
     * no such object can be found.
     */
