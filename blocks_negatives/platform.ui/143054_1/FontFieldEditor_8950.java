    /**
     * The change font button, or <code>null</code> if none
     * (before creation and after disposal).
     */
    private Button changeFontButton = null;

    /**
     * The text for the change font button, or <code>null</code>
     * if missing.
     */
    private String changeButtonText;

    /**
     * The text for the preview, or <code>null</code> if no preview is desired
     */
    private String previewText;

    /**
     * Font data for the chosen font button, or <code>null</code> if none.
     */
    private FontData[] chosenFont;

    /**
     * The label that displays the selected font, or <code>null</code> if none.
     */
    private Label valueControl;

    /**
     * The previewer, or <code>null</code> if none.
     */
    private DefaultPreviewer previewer;

    /**
     * Internal font previewer implementation.
     */
    private static class DefaultPreviewer {
        private Text text;

        private String string;

        private Font font;

        /**
         * Constructor for the previewer.
         * @param s
         * @param parent
         */
        public DefaultPreviewer(String s, Composite parent) {
            string = s;
            text = new Text(parent, SWT.READ_ONLY | SWT.BORDER);
            text.addDisposeListener(e -> {
			    if (font != null) {
