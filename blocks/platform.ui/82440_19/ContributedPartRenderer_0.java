		IEclipseContext localContext = part.getContext();

		Widget parentWidget = (Widget) parent;

		Composite partComposite = localContext.getLocal(Composite.class);


		if (partComposite == null) {
