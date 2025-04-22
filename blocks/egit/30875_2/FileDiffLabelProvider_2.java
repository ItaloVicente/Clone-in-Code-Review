
	@Override
	public Font getFont(Object element) {
		final FileDiff c = (FileDiff) element;
		if (!allAreInteresting
				&& c.isMarked(FileDiffContentProvider.INTERESTING_MARK_TREE_FILTER_INDEX)) {
			if (boldFont == null) {
				Font font = UIUtils
						.getFont(UIPreferences.THEME_CommitGraphHighlightFont);
				FontData fontData[] = font.getFontData();
				for (int i = 0; i < fontData.length; i++) {
					fontData[i].setStyle(fontData[i].getStyle() | SWT.BOLD);
				}
				boldFont = resourceManager.createFont(FontDescriptor
						.createFrom(fontData));
			}
			return boldFont;
		}
		return super.getFont(element);
	}

	void setAllInteresting(boolean all) {
		this.allAreInteresting = all;
	}

