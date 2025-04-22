	private void checkAllItemsIfSelectAllEventIsFired(SelectionEvent e) {
		if (e.item == null) {
			projectNames.setAllChecked(true);
			checkStateChanged();
		}
	}

