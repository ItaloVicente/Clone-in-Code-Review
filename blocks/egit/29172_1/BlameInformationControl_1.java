
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if (!visible) {
			setInput(null);
		}
	}
