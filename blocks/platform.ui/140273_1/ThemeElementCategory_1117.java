public class ThemeElementCategory implements IPluginContribution, IThemeElementDefinition {

	private String description;

	private IConfigurationElement element;

	private String id;

	private String parentId;

	private String label;

	private String pluginId;

	public ThemeElementCategory(String label, String id, String parentId, String description, String pluginId,
			IConfigurationElement element) {

		this.label = label;
		this.id = id;
		this.parentId = parentId;
		this.description = description;
		this.pluginId = pluginId;
		this.element = element;
	}

	public IThemePreview createPreview() throws CoreException {
		String classString = element.getAttribute(IWorkbenchRegistryConstants.ATT_CLASS);
		if (classString == null || "".equals(classString)) { //$NON-NLS-1$
