                StyledText text = (StyledText) event.widget;
                if (event.character == ' ' || event.character == SWT.CR) {
                    if (item != null) {
                        int offset = text.getSelection().x + 1;

                        if (item.isLinkAt(offset)) {
                            text.setCursor(busyCursor);
                            AboutUtils.openLink(styledText.getShell(), item.getLinkAt(offset));
                            StyleRange selectionRange = getCurrentRange();
                            text.setSelectionRange(selectionRange.start,
                                    selectionRange.length);
                            text.setCursor(null);
                        }
                    }
                    return;
                }
            }
        });
    }

    /**
     * Gets the about item.
     * @return the about item
     */
    public AboutItem getItem() {
        return item;
    }

    /**
     * Sets the about item.
     * @param item about item
     */
    public void setItem(AboutItem item) {
        this.item = item;
        if (item != null) {
        	styledText.setText(item.getText());
        	setLinkRanges(item.getLinkRanges());
        }
    }

    /**
     * Find the range of the current selection.
     */
    private StyleRange getCurrentRange() {
        StyleRange[] ranges = styledText.getStyleRanges();
        int currentSelectionEnd = styledText.getSelection().y;
        int currentSelectionStart = styledText.getSelection().x;

        for (StyleRange range : ranges) {
            if ((currentSelectionStart >= range.start)
                    && (currentSelectionEnd <= (range.start + range.length))) {
                return range;
            }
        }
        return null;
    }

    /**
     * Find the next range after the current
     * selection.
     */
    private StyleRange findNextRange() {
        StyleRange[] ranges = styledText.getStyleRanges();
        int currentSelectionEnd = styledText.getSelection().y;

        for (StyleRange range : ranges) {
            if (range.start >= currentSelectionEnd) {
