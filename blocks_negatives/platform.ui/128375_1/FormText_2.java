		Paragraph[] paragraphs = model.getParagraphs();
		IHyperlinkSegment selectedLink = getSelectedLink();
		if (getDisplay().getFocusControl() != this)
			selectedLink = null;
		for (Paragraph p : paragraphs) {
			p
					.paint(textGC, repaintRegion, resourceTable, selectedLink,
							selData);
