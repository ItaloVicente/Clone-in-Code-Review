		if (item.getMarker() == null) {
			int severity = ((MarkerCategory) item).getHighestSeverity();
			if (severity >= IMarker.SEVERITY_WARNING)
				return MarkerSupportInternalUtilities.getSeverityImage(severity);
			return null;
		}
		int severity = MarkerSupportInternalUtilities.getSeverity(item);
		return MarkerSupportInternalUtilities.getSeverityImage(severity);
