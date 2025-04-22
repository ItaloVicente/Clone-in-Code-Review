	String FILTER_ID_EXPERT = "org.eclipse.ui.views.properties.expert"; //$NON-NLS-1$

	void addPropertySheetEntryListener(
			IPropertySheetEntryListener listener);

	void applyEditorValue();

	void dispose();

	String getCategory();

	IPropertySheetEntry[] getChildEntries();

	String getDescription();

	String getDisplayName();

	CellEditor getEditor(Composite parent);

	String getErrorText();

	String[] getFilters();

	Object getHelpContextIds();

	Image getImage();

	String getValueAsString();

	boolean hasChildEntries();

	void removePropertySheetEntryListener(
			IPropertySheetEntryListener listener);

	void resetPropertyValue();

	void setValues(Object[] values);
