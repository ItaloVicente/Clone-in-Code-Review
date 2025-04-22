	private Control textControl;

	private DiffEditorOutlinePage outlinePage;

	private Annotation[] currentFoldingAnnotations;

	private Annotation[] currentOverviewAnnotations;

	/** An {@link IPreferenceStore} for the annotation colors.*/
	private ThemePreferenceStore overviewStore;

	private FileDiffRegion currentFileDiffRange;

	private OldNewLogicalLineNumberRulerColumn lineNumberColumn;

	private boolean plainLineNumbers = false;

