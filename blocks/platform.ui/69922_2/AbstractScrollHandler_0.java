	public boolean mousePosOverScroll(Scrollable scrollable, Point controlPos) {
		if (this.fScrollBar == null || !this.fVisible || fScrollBarPositions == null
				|| !this.fScrollBarSettings.getScrollBarThemed()) {
			return false;
		}
		Rectangle currClientArea = scrollable.getClientArea();
		Rectangle fullRect = this.getFullBackgroundRect(scrollable, currClientArea, true);
		if (fullRect != null) {
			boolean ret = fullRect.contains(controlPos.x, controlPos.y);
			return ret;
		}
		return false;
	}

