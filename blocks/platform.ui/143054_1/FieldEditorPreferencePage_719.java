	protected FieldEditorPreferencePage(int style) {
		super();
		this.style = style;
	}

	protected FieldEditorPreferencePage(String title, int style) {
		super(title);
		this.style = style;
	}

	protected FieldEditorPreferencePage(String title, ImageDescriptor image,
			int style) {
		super(title, image);
		this.style = style;
	}

	protected void addField(FieldEditor editor) {
		if (fields == null) {
