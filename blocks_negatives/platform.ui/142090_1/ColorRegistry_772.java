     * This registries <code>Display</code>. All colors will be allocated using
     * it.
     */
    protected Display display;

    /**
     * Collection of <code>Color</code> that are now stale to be disposed when
     * it is safe to do so (i.e. on shutdown).
     */
    private List<Color> staleColors = new ArrayList<>();

    /**
     * Table of known colors, keyed by symbolic color name (key type: <code>String</code>,
     * value type: <code>org.eclipse.swt.graphics.Color</code>.
     */
    private Map<String, Color> stringToColor = new HashMap<>(7);

    /**
     * Table of known color data, keyed by symbolic color name (key type:
     * <code>String</code>, value type: <code>org.eclipse.swt.graphics.RGB</code>).
     */
    private Map<String, RGB> stringToRGB = new HashMap<>(7);

    /**
     * Runnable that cleans up the manager on disposal of the display.
     */
    protected Runnable displayRunnable = this::clearCaches;
