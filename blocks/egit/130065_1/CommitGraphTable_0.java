
		int textx;
		if (column.getAlignment() == SWT.RIGHT) {
			int columnWidth = column.getWidth();
			textx = (columnWidth - textsz.x) - TEXT_INDENTATION;
		} else {
			textx = TEXT_INDENTATION;
		}
		event.gc.drawString(txt, event.x + textx, event.y + texty, true);
