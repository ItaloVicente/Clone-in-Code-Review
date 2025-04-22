	@Override
	public void setBackground(Color color) {
		bgCol = color;
		button.redraw();
		button.update();
	}
