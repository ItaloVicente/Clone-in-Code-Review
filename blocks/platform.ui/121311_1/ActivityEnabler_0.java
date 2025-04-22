	private Set managedActivities = new HashSet(7);

	protected ActivityCategoryContentProvider provider = new ActivityCategoryContentProvider();

	protected Text descriptionText;

	private Properties strings;

	private IMutableActivityManager activitySupport;

	private TableViewer dependantViewer;

	private ISelectionChangedListener selectionListener = event -> {
		Object element = event.getStructuredSelection().getFirstElement();
		try {
			if (element instanceof ICategory) {
				descriptionText.setText(((ICategory) element).getDescription());
			} else if (element instanceof IActivity) {
				descriptionText.setText(((IActivity) element).getDescription());
