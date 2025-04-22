		if (obj == this) {
			return true;
		}
		if (obj == null || obj.getClass() != getClass()) {
			return false;
		}
		return fieldsEqual((HistoryPageInput) obj);
	}

	public boolean baseEquals(Object obj) {
