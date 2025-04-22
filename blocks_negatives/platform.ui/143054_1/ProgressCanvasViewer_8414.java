        Assert.isTrue(labelProvider instanceof ILabelProvider);
        super.setLabelProvider(labelProvider);
    }

    /**
     * Get the size hints for the receiver. These are used for
     * layout data.
     * @return Point - the preferred x and y coordinates
     */
    public Point getSizeHints() {

        Display display = canvas.getDisplay();

        GC gc = new GC(canvas);
        FontMetrics fm = gc.getFontMetrics();
        int charWidth = fm.getAverageCharWidth();
        int charHeight = fm.getHeight();
        int maxWidth = display.getBounds().width / 2;
        int maxHeight = display.getBounds().height / 6;
        int fontWidth = charWidth * maxCharacterWidth;
        int fontHeight = charHeight * numShowItems;
        if (maxWidth < fontWidth) {
