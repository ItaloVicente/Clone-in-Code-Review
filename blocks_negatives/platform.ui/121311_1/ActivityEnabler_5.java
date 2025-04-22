	protected CheckboxTreeViewer dualViewer;

	/**
	 * The Set of activities that belong to at least one category.
	 */
	private Set managedActivities = new HashSet(7);

	/**
	 * The content provider.
	 */
	protected ActivityCategoryContentProvider provider = new ActivityCategoryContentProvider();

	/**
	 * The descriptive text.
	 */
	protected Text descriptionText;

	private Properties strings;

    private IMutableActivityManager activitySupport;

	private TableViewer dependantViewer;

