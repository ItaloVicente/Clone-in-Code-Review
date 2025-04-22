public class ThemeElementCategory implements IPluginContribution,
        IThemeElementDefinition {

    private String description;

    private IConfigurationElement element;

    private String id;

    private String parentId;

    private String label;

    private String pluginId;

    /**
     *
     * @param label
     * @param id
     * @param parentId
     * @param description
     * @param pluginId
     * @param element
     */
    public ThemeElementCategory(String label, String id, String parentId,
            String description, String pluginId, IConfigurationElement element) {

        this.label = label;
        this.id = id;
        this.parentId = parentId;
        this.description = description;
        this.pluginId = pluginId;
        this.element = element;
    }

    /**
     * @return Returns the <code>IColorExample</code> for this category.  If one
     * is not available, <code>null</code> is returned.
     * @throws CoreException thrown if there is a problem instantiating the preview
     */
    public IThemePreview createPreview() throws CoreException {
        String classString = element.getAttribute(IWorkbenchRegistryConstants.ATT_CLASS);
