
	public Cursor getArrowCursor() {
		return arrowCursor;
	}

	public void setArrowCursor(Cursor arrowCursor) {
		if (this.arrowCursor != null) {
			this.arrowCursor.dispose();
		}
		this.arrowCursor = arrowCursor;
	}
