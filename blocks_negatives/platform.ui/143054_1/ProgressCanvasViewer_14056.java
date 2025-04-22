    /**
     * Create a new instance of the receiver with the supplied
     * parent and style bits.
     * @param parent The composite the Canvas is created in
     * @param style style bits for the canvas
     * @param itemsToShow the number of items this will show
     * @param numChars The number of characters for the width hint.
     * @param side the side to display text, this helps determine horizontal vs vertical
     */
    ProgressCanvasViewer(Composite parent, int style, int itemsToShow, int numChars, int orientation) {
        super();
        this.orientation = orientation;
        numShowItems = itemsToShow;
        maxCharacterWidth = numChars;
        canvas = new Canvas(parent, style);
        hookControl(canvas);
        GC gc = new GC(canvas);
        gc.setFont(JFaceResources.getDefaultFont());
        fontMetrics = gc.getFontMetrics();
        gc.dispose();
        initializeListeners();
    }

    /**
     * NE: Copied from ContentViewer.  We don't want the OpenStrategy hooked
     * in StructuredViewer.hookControl otherwise the canvas will take focus
     * since it has a key listener.  We don't want this included in the window's
     * tab traversal order.  Defeating it here is more self-contained then
     * setting the tab list on the shell or other parent composite.
     */
    @Override
