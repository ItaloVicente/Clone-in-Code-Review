	public void remove(IEditorInput input) {
		if (input == null) {
			return;
		}
		Iterator iter = fifoList.iterator();
		while (iter.hasNext()) {
			EditorHistoryItem item = (EditorHistoryItem) iter.next();
			if (item.matches(input)) {
				iter.remove();
			}
		}
	}
