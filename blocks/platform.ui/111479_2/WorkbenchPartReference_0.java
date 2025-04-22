	private void handleContextSet(Event event) {
		Object element = event.getProperty(UIEvents.EventTags.ELEMENT);
		MPart part = getModel();
		if (element == part) {
			IEclipseContext context = part.getContext();
			if (context != null) {
				context.set(getClass().getName(), this);
				unsubscribe();
			}
		}
	}

