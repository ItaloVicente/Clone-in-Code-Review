		context.set(PreferenceManager.class.getName(), new ContextFunction() {
			@Override
			public Object compute(IEclipseContext context, String contextKey) {
				if (preferenceManager == null) {
					preferenceManager = new WorkbenchPreferenceManager(
							PREFERENCE_PAGE_CATEGORY_SEPARATOR);
