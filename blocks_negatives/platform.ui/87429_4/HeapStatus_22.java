    private final IPropertyChangeListener prefListener = new IPropertyChangeListener() {
		@Override
		public void propertyChange(PropertyChangeEvent event) {
			if (IHeapStatusConstants.PREF_UPDATE_INTERVAL.equals(event.getProperty())) {
				setUpdateIntervalInMS(prefStore.getInt(IHeapStatusConstants.PREF_UPDATE_INTERVAL));
			}
			else if (IHeapStatusConstants.PREF_SHOW_MAX.equals(event.getProperty())) {
				showMax = prefStore.getBoolean(IHeapStatusConstants.PREF_SHOW_MAX);
			}
