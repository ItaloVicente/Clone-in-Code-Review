	private IPropertyListener propertyListener = (source, propId) -> {
		switch (propId) {
		case IWorkbenchPartConstants.PROP_TITLE:
			titleChangeEvent = true;
			break;
		case IWorkbenchPartConstants.PROP_PART_NAME:
			nameChangeEvent = true;
			break;
		case IWorkbenchPartConstants.PROP_CONTENT_DESCRIPTION:
			contentChangeEvent = true;
			break;
