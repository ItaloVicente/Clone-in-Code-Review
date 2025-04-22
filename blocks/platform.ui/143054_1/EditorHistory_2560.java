	public EditorHistoryItem[] getItems() {
		refresh();
		EditorHistoryItem[] array = new EditorHistoryItem[fifoList.size()];
		fifoList.toArray(array);
		return array;
	}
