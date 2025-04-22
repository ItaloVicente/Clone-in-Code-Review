	private boolean getSortCheckState() {
		if (memento == null) {
			return false;
		}
		Boolean checked = memento.getBoolean(STORE_SORT_STATE);
		if (checked == null) {
			return false;
		}
		return checked.booleanValue();

	}

