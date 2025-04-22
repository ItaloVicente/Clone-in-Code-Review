	@Override
	public String getText(Object element) {
		String label = WorkbenchMessages.ViewLabel_unknown;
		if (element instanceof String) {
			label = (String) element;
		} else if (element instanceof MPartDescriptor) {
			label = ((MPartDescriptor) element).getLocalizedLabel();
		}
		return label;
	}

