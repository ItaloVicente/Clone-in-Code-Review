    /**
     * The default extent in pixels.
     */
    private static final int DEFAULT_EXTENT = 16;

    /**
     * Gap between between image and text in pixels.
     */
    private static final int GAP = 6;

    /**
     * The composite widget containing the color and RGB label widgets
     */
    private Composite composite;

    /**
     * The label widget showing the current color.
     */
    private Label colorLabel;

    /**
     * The label widget showing the RGB values.
     */
    private Label rgbLabel;

    /**
     * The image.
     */
    private Image image;

    /**
     * Internal class for laying out this cell editor.
     */
    private class ColorCellLayout extends Layout {
        @Override
