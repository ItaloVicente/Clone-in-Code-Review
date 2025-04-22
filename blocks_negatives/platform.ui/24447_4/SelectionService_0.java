			selection = createCompatibilitySelection(selection);
			context.set(ISources.ACTIVE_CURRENT_SELECTION_NAME, selection);

			IEclipseContext applicationContext = application.getContext();
			if (applicationContext.getActiveChild() == context) {
				application.getContext().set(ISources.ACTIVE_CURRENT_SELECTION_NAME, selection);
			}
