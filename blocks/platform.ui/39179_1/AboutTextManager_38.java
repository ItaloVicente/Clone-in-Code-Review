				StyledText text = (StyledText) event.widget;
				if (event.character == ' ' || event.character == SWT.CR) {
					if (item != null) {
						int offset = text.getSelection().x + 1;

						if (item.isLinkAt(offset)) {
							text.setCursor(busyCursor);
							AboutUtils.openLink(styledText.getShell(), item.getLinkAt(offset));
							StyleRange selectionRange = getCurrentRange();
							text.setSelectionRange(selectionRange.start, selectionRange.length);
							text.setCursor(null);
						}
					}
					return;
				}
			}
		});
	}

	public AboutItem getItem() {
		return item;
	}

	public void setItem(AboutItem item) {
		this.item = item;
		if (item != null) {
			styledText.setText(item.getText());
			setLinkRanges(item.getLinkRanges());
		}
	}

	private StyleRange getCurrentRange() {
		StyleRange[] ranges = styledText.getStyleRanges();
		int currentSelectionEnd = styledText.getSelection().y;
		int currentSelectionStart = styledText.getSelection().x;

		for (int i = 0; i < ranges.length; i++) {
			if ((currentSelectionStart >= ranges[i].start)
					&& (currentSelectionEnd <= (ranges[i].start + ranges[i].length))) {
				return ranges[i];
			}
		}
		return null;
	}

	private StyleRange findNextRange() {
		StyleRange[] ranges = styledText.getStyleRanges();
		int currentSelectionEnd = styledText.getSelection().y;

		for (int i = 0; i < ranges.length; i++) {
			if (ranges[i].start >= currentSelectionEnd) {
