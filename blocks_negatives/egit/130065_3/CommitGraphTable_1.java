		if (event.index == 1) {
			renderer.paint(event, input == null ? null : input.getHead());
			return;
		}

		final ITableLabelProvider lbl;
		final String txt;

		lbl = (ITableLabelProvider) table.getLabelProvider();
		txt = lbl.getColumnText(c, event.index);

		final Point textsz = event.gc.textExtent(txt);
		final int texty = (event.height - textsz.y) / 2;
		event.gc.drawString(txt, event.x, event.y + texty, true);
