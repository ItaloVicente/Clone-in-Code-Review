	private final IPropertyChangeListener propertyChangeListener = new IPropertyChangeListener() {

		@Override
		public void propertyChange(PropertyChangeEvent event) {
			if (!saving && event.getProperty().startsWith(PREFIX)) {
				String activityId = event.getProperty().substring(PREFIX.length());
				IWorkbenchActivitySupport support = PlatformUI.getWorkbench().getActivitySupport();
				IActivityManager activityManager = support.getActivityManager();

				boolean enabled = Boolean.parseBoolean(event.getNewValue().toString());
				Set<String> set = new HashSet<>(activityManager.getEnabledActivityIds());
				if (enabled == false) {
					Set<String> dependencies = buildDependencies(activityManager, activityId);
					set.removeAll(dependencies);
				} else {
					set.add(activityId);
				}
				support.setEnabledActivityIds(set);
