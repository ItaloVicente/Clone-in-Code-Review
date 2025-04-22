		}

		if (sampleStyledText == null) {
			this.scrolledComposite.getHorizontalBar().setIncrement(
					HORZ_SCROLL_INCREMENT);
			this.scrolledComposite.getVerticalBar().setIncrement(
					VERT_SCROLL_INCREMENT);
		} else {
			GC gc = new GC(sampleStyledText);
			int width = gc.getFontMetrics().getAverageCharWidth();
			gc.dispose();
			this.scrolledComposite.getHorizontalBar().setIncrement(width);
			this.scrolledComposite.getVerticalBar().setIncrement(
					sampleStyledText.getLineHeight());
		}
		return infoArea;
	}

	@Override
