		ICategorizedThemeElementDefinition, IEditable, IFontDefinitionOverridable {

	private final static ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(Theme.class
			.getName());

	private String label;

	private String id;

	private String defaultsTo;

	private String categoryId;

	private String description;

	private String value;

	private boolean isEditable;

	private boolean overriden;

	private FontData[] parsedValue;

	public FontDefinition(String fontName, String uniqueId, String defaultsId, String value,
			String categoryId, boolean isEditable, String fontDescription) {
		this.label = fontName;
		this.id = uniqueId;
		this.defaultsTo = defaultsId;
		this.value = value;
		this.categoryId = categoryId;
		this.description = fontDescription;
		this.isEditable = isEditable;
	}

	public FontDefinition(FontDefinition originalFont, FontData[] datas) {
		this.label = originalFont.getName();
		this.id = originalFont.getId();
		this.categoryId = originalFont.getCategoryId();
		this.description = originalFont.getDescription();
		this.isEditable = originalFont.isEditable();
		this.parsedValue = datas;
	}

	public String getDefaultsTo() {
		return defaultsTo;
	}

	public String getDescription() {
		return description;
	}

	public String getName() {
		return label;
	}

	public String getId() {
		return id;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public FontData[] getValue() {
		if (value == null) {
