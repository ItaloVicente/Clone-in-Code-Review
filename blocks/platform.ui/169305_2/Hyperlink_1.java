			else {
				super.setToolTipText(appToolTipText);
			}
			gc.drawText(textToDraw, bounds.x, bounds.y, true);
			if (underlined) {
				int descent = gc.getFontMetrics().getDescent();
				int lineY = bounds.y + textHeight - descent + 1;
				gc.drawLine(bounds.x, lineY, bounds.x + textWidth, lineY);
			}
