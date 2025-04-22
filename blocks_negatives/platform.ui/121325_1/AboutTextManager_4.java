    private void createCursors() {
        handCursor = new Cursor(styledText.getDisplay(), SWT.CURSOR_HAND);
        busyCursor = new Cursor(styledText.getDisplay(), SWT.CURSOR_WAIT);
        styledText.addDisposeListener(e -> {
		    handCursor.dispose();
		    handCursor = null;
		    busyCursor.dispose();
		    busyCursor = null;
		});
    }


