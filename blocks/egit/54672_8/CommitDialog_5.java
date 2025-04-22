	private int getProblemsSeverity() {
		int result = IProblemDecoratable.SEVERITY_NONE;
		Object[] checkedElements = filesViewer.getCheckedElements();
		ArrayList<String> selectedFiles = new ArrayList<>();
		for (Object obj : checkedElements)
			selectedFiles.add(((CommitItem) obj).path);
		for (final CommitItem item : items) {
			if (item.getProblemSeverity() >= IMarker.SEVERITY_WARNING
					&& selectedFiles.contains(item.path)) {
				if (result < item.getProblemSeverity()) {
					result = item.getProblemSeverity();
				}
			}
		}
		return result;
	}

