		} else // failed to create the widget, dispose its context if necessary
		if (element instanceof MContext) {
			MContext ctxt = (MContext) element;
			IEclipseContext lclContext = ctxt.getContext();
			if (lclContext != null) {
				lclContext.dispose();
				ctxt.setContext(null);
