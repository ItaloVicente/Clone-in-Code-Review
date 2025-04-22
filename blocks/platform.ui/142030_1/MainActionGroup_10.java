	public MainActionGroup(IResourceNavigator navigator) {
		super(navigator);
		resourceChangeListener = event -> handleResourceChanged(event);
		ResourcesPlugin.getWorkspace().addResourceChangeListener(resourceChangeListener,
				IResourceChangeEvent.POST_CHANGE);
		makeSubGroups();
	}
