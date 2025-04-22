	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if (visible && refName != null)
			refText.setText(refName);
	}

