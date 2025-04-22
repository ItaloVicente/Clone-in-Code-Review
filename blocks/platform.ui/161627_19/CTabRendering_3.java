	@Override
	public void setSelectedTabHighlightTop(boolean drawTabHiglightOnTop) {
		this.drawTabHighlightOnTop = drawTabHiglightOnTop;
		parent.redraw();
	}

