	private ListenerList propertyChangeListeners = new ListenerList();

	private IActionBars actionBars;

	private ActionSetManager actionSets;

	private NavigationHistory navigationHistory = new NavigationHistory(this);

	private IWorkbenchPartReference partBeingActivated = null;

	private IPropertyChangeListener workingSetPropertyChangeListener = new IPropertyChangeListener() {
		public void propertyChange(PropertyChangeEvent event) {
			String property = event.getProperty();
			if (IWorkingSetManager.CHANGE_WORKING_SET_REMOVE.equals(property)) {
				if (event.getOldValue().equals(workingSet)) {
					setWorkingSet(null);
				}

