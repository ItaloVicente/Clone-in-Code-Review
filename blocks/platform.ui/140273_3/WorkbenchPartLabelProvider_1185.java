		if (element instanceof IWorkbenchPart) {
			return ((IWorkbenchPart) element).getTitleImage();
		}
		if (element instanceof Saveable) {
			Saveable model = (Saveable) element;
			ImageDescriptor imageDesc = model.getImageDescriptor();
			if (imageDesc == null) {
