        return false;
    }

    /**
     * Set the text in the editor.
     */
    public void setText(String text) {
        ExtendedSourceViewer viewer = (ExtendedSourceViewer) getSourceViewer();
        StyledText widget = viewer.getTextWidget();
        widget.setText(text);
        viewer.setSelectedRange(0, text.length());
    }
