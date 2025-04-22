		for (IMarker marker : markers) {
			report.append(MarkerUtil.getMessage(marker)).append('\t');
			report.append(MarkerUtil.getResourceName(marker)).append('\t');
			report.append(MarkerUtil.getContainerName(marker)).append('\t');
			int line = MarkerUtil.getLineNumber(marker);
			report.append(NLS.bind(BookmarkMessages.LineIndicator_text, String.valueOf(line)));
			report.append(System.getProperty("line.separator")); //$NON-NLS-1$
		}
