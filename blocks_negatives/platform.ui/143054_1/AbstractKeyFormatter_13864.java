    /**
     * The key for the delimiter between keys. This is used in the
     * internationalization bundles.
     */

    /**
     * The key for the delimiter between key strokes. This is used in the
     * internationalization bundles.
     */

    /**
     * The bundle in which to look up the internationalized text for all of the
     * individual keys in the system. This is the platform-agnostic version of
     * the internationalized strings. Some platforms (namely Carbon) provide
     * special Unicode characters and glyphs for some keys.
     */
    private final static ResourceBundle RESOURCE_BUNDLE = ResourceBundle
            .getBundle(AbstractKeyFormatter.class.getName());

    @Override
