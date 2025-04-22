		try {
			gc.setForeground(fg);
			if ((getStyle() & SWT.WRAP) != 0) {
				FormUtil.paintWrapText(gc, text, bounds, underlined);
			} else {
				Point totalSize = computeTextSize(SWT.DEFAULT, SWT.DEFAULT);
				boolean shortenText =false;
				if (bounds.width<totalSize.x) {
					shortenText=true;
				}
				int textWidth = Math.min(bounds.width, totalSize.x);
				int textHeight = totalSize.y;
				String textToDraw = getText();
				if (shortenText) {
					textToDraw = shortenText(gc, getText(), bounds.width);
					if (appToolTipText == null) {
						super.setToolTipText(getText());
					}
				}
				else {
					super.setToolTipText(appToolTipText);
				}
				gc.drawText(textToDraw, bounds.x, bounds.y, true);
				if (underlined) {
					int descent = gc.getFontMetrics().getDescent();
					int lineY = bounds.y + textHeight - descent + 1;
					gc.drawLine(bounds.x, lineY, bounds.x + textWidth, lineY);
