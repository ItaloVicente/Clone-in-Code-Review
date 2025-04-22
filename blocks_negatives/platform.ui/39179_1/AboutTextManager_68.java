	
    private Cursor handCursor;

    private Cursor busyCursor;

    private boolean mouseDown = false;

    private boolean dragEvent = false;
    
    private AboutItem item;
    
    public AboutTextManager(StyledText text) {
    	this.styledText = text;
    	createCursors();
    	addListeners();
    }
    
    private void createCursors() {
        handCursor = new Cursor(styledText.getDisplay(), SWT.CURSOR_HAND);
        busyCursor = new Cursor(styledText.getDisplay(), SWT.CURSOR_WAIT);
        styledText.addDisposeListener(new DisposeListener() {
            @Override
