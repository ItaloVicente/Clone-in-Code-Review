		if (clearLabel != null) {
			clearLabel.setVisible(visible);
			GridData layoutData = (GridData) clearLabel.getLayoutData();
			layoutData.exclude = !visible;
			clearLabel.getParent().requestLayout();
		}
