
		int severity = -1;
		if (item.getMarker() == null)
			severity = ((MarkerCategory) item).getHighestSeverity();
		else
			severity = MarkerSupportInternalUtilities.getSeverity(item);

		if (severity >= IMarker.SEVERITY_WARNING)
			return MarkerSupportInternalUtilities.getSeverityImage(severity);
		return null;

