        }
    }

    /**
     * Table of known fonts, keyed by symbolic font name
     * (key type: <code>String</code>,
     *  value type: <code>FontRecord</code>.
     */
    private Map<String, FontRecord> stringToFontRecord = new HashMap<>(7);

    /**
     * Table of known font data, keyed by symbolic font name
     * (key type: <code>String</code>,
     *  value type: <code>org.eclipse.swt.graphics.FontData[]</code>).
     */
    private Map<String, FontData[]> stringToFontData = new HashMap<>(7);

    /**
     * Collection of Fonts that are now stale to be disposed
     * when it is safe to do so (i.e. on shutdown).
     * @see List
     */
    private List<Font> staleFonts = new ArrayList<>();

    /**
     * Runnable that cleans up the manager on disposal of the display.
     */
