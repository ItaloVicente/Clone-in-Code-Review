	public void refresh() {
		Iterator iter = fifoList.iterator();
		while (iter.hasNext()) {
			EditorHistoryItem item = (EditorHistoryItem) iter.next();
			if (item.isRestored()) {
				IEditorInput input = item.getInput();
				if (input != null && !input.exists()) {
