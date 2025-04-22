		if (!(id instanceof String)) {
			openOther(event, workbenchWindow, app, partService);
			return null;
		}

		MPartDescriptor viewDescriptor = getViewDescriptor(app, (String) id);
		if (viewDescriptor == null) {
			handleMissingView(id);
			return null;
