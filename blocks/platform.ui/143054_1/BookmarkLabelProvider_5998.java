		IMarker marker = (IMarker) element;

		switch (columnIndex) {
		case COLUMN_DESCRIPTION:
			return marker.getAttribute(IMarker.MESSAGE, ""); //$NON-NLS-1$
		case COLUMN_RESOURCE:
			return marker.getResource().getName();
		case COLUMN_FOLDER:
			return getContainerName(marker);
		case COLUMN_LOCATION: {
			int line = marker.getAttribute(IMarker.LINE_NUMBER, -1);
			if (line == -1) {
