        }
        return null;
    }

    /**
     * Finds the current link of the current selection.
     */
    protected StyleRange getCurrentLink(StyledText text) {
        int currentSelectionEnd = text.getSelection().y;
        int currentSelectionStart = text.getSelection().x;

        for (StyleRange range : text.getStyleRanges()) {
            if ((currentSelectionStart >= range.start)
                    && (currentSelectionEnd <= (range.start + range.length))) {
                return range;
            }
        }
        return null;
    }

    /**
     * Adds listeners to the given styled text
     */
    private void addListeners(StyledText styledText) {
