			int width = 0;
			if (wHint == SWT.DEFAULT) {
				width += toggleWidthPlusGap;
				width += labelDefault.x;
				width += gapBetweenTcAndLabel;
				width += tcDefault.x;
			} else {
				width = wHint - marginWidth - marginWidth - thmargin - thmargin;
