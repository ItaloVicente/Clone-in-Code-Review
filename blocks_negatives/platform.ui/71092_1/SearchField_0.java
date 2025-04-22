		text.setToolTipText(QuickAccessMessages.QuickAccess_TooltipDescription);

		FontData[] fD = text.getFont().getFontData();
		int round = (int) Math.round(fD[0].getHeight() * 0.8);
		fD[0].setHeight(round);
		font = new Font(text.getDisplay(), fD[0]);
		text.setFont(font);

