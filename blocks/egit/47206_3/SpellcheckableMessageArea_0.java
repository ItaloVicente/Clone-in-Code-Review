	@Override
	public void setBackground(Color color) {
		super.setBackground(color);
		StyledText textWidget = getTextWidget();
		textWidget.setBackground(color);
	}

