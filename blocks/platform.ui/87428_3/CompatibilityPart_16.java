		wrapped.addPropertyListener((source, propId) -> {
			switch (propId) {
			case IWorkbenchPartConstants.PROP_TITLE:
				part.setLabel(computeLabel());

				if (wrapped.getTitleImage() != null) {
					Image newImage = wrapped.getTitleImage();
					part.getTransientData().put(IPresentationEngine.OVERRIDE_ICON_IMAGE_KEY,
							newImage);
