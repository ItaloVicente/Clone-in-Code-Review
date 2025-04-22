		getTextWidget().setFont(getMonospaceFont());

		int endSpacing = 2;
		int textWidth = getCharWidth() * 70 + endSpacing;
		int textHeight = getLineHeight() * 10;
		Point size = getTextWidget().computeSize(textWidth, textHeight);
		getTextWidget().setSize(size);
