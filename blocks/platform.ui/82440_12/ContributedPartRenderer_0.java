		IEclipseContext localContext = part.getContext();

		Widget parentWidget = (Widget) parent;

		Composite partComposite = localContext.get(Composite.class);


		if (partComposite == null) {
