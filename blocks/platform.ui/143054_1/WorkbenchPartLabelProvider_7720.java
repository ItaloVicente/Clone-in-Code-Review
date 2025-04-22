	@Override
	public String getText(Object element) {
		if (element instanceof IWorkbenchPart) {
			IWorkbenchPart part = (IWorkbenchPart) element;
			String path = part.getTitleToolTip();
			if (path == null || path.trim().length() == 0) {
				return part.getTitle();
			}
			return part.getTitle() + "  [" + path + "]"; //$NON-NLS-1$ //$NON-NLS-2$
		}
		if (element instanceof Saveable) {
			Saveable model = (Saveable) element;
			String path = model.getToolTipText();
			if (path == null || path.trim().length() == 0) {
				return model.getName();
			}
			return model.getName() + "  [" + path + "]"; //$NON-NLS-1$ //$NON-NLS-2$
