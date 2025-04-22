			if (segmentUnder instanceof IHyperlinkSegment) {
				IHyperlinkSegment linkUnder = (IHyperlinkSegment) segmentUnder;
				if (entered!=null && linkUnder!=entered) {
					exitLink(entered, e.stateMask);
					paintLinkHover(entered, false);
					entered = null;
				}
				if (entered == null) {
					entered = linkUnder;
					enterLink(linkUnder, e.stateMask);
					paintLinkHover(entered, true);
					setCursor(model.getHyperlinkSettings().getHyperlinkCursor());
				}
			} else {
				if (entered != null) {
					exitLink(entered, e.stateMask);
					paintLinkHover(entered, false);
					entered = null;
				}
				if (segmentUnder instanceof TextSegment)
					setCursor(model.getHyperlinkSettings().getTextCursor());
				else
					setCursor(null);
