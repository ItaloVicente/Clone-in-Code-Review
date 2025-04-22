		addToHistory(l, newEntry);
		String[] r = new String[l.size()];
		l.toArray(r);
		return r;
	}

	protected void addToHistory(List history, String newEntry) {
		history.remove(newEntry);
		history.add(0, newEntry);

		if (history.size() > COMBO_HISTORY_LENGTH) {
