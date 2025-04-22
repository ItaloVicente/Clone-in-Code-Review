	public void setTextSelectable(boolean selectable) {
		if (selectable) {
			this.titleText.setVisible(currentTitleControl.getVisible());
			this.titleLabel.setVisible(false);
			this.currentTitleControl = this.titleText;
		} else {
			this.titleLabel.setVisible(currentTitleControl.getVisible());
			this.titleText.setVisible(false);
			this.currentTitleControl = this.titleLabel;
		}
		layout();
		redraw();
	}

