	
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
            public void widgetDisposed(DisposeEvent e) {
                handCursor.dispose();
                handCursor = null;
                busyCursor.dispose();
                busyCursor = null;
            }
        });
    }

	
    /**
     * Adds listeners to the given styled text
     */
    protected void addListeners() {
        styledText.addMouseListener(new MouseAdapter() {
            public void mouseDown(MouseEvent e) {
                if (e.button != 1) {
                    return;
                }
                mouseDown = true;
            }

            public void mouseUp(MouseEvent e) {
                mouseDown = false;
                int offset = styledText.getCaretOffset();
                if (dragEvent) {
                    dragEvent = false;
                    if (item != null && item.isLinkAt(offset)) {
                    	styledText.setCursor(handCursor);
                    }
                } else if (item != null && item.isLinkAt(offset)) {
                	styledText.setCursor(busyCursor);
                    AboutUtils.openLink(styledText.getShell(), item.getLinkAt(offset));
                    StyleRange selectionRange = getCurrentRange();
                    styledText.setSelectionRange(selectionRange.start,
                            selectionRange.length);
                    styledText.setCursor(null);
                }
            }
        });

        styledText.addMouseMoveListener(new MouseMoveListener() {
            public void mouseMove(MouseEvent e) {
                if (mouseDown) {
                    if (!dragEvent) {
                        StyledText text = (StyledText) e.widget;
                        text.setCursor(null);
                    }
                    dragEvent = true;
                    return;
                }
                StyledText text = (StyledText) e.widget;
                int offset = -1;
                try {
                    offset = text.getOffsetAtLocation(new Point(e.x, e.y));
                } catch (IllegalArgumentException ex) {
                }
                if (offset == -1) {
