	private ListenerList propertyChangeListeners = new ListenerList();

	private IActionBars actionBars;

	private ActionSetManager actionSets;

	private NavigationHistory navigationHistory = new NavigationHistory(this);

	private IWorkbenchPartReference partBeingActivated = null;

	private IPropertyChangeListener workingSetPropertyChangeListener = new IPropertyChangeListener() {
		@Override
