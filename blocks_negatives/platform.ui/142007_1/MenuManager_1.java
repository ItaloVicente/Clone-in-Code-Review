    /**
     * Creates a menu manager.  The text and id are <code>null</code>.
     * Typically used for creating a context menu, where it doesn't need to be referred to by id.
     */
    public MenuManager() {
        this(null, null, null);
    }

    /**
     * Creates a menu manager with the given text. The id of the menu
     * is <code>null</code>.
     * Typically used for creating a sub-menu, where it doesn't need to be referred to by id.
     *
     * @param text the text for the menu, or <code>null</code> if none
     */
    public MenuManager(String text) {
        this(text, null, null);
    }

    /**
     * Creates a menu manager with the given text and id.
     * Typically used for creating a sub-menu, where it needs to be referred to by id.
     *
     * @param text the text for the menu, or <code>null</code> if none
     * @param id the menu id, or <code>null</code> if it is to have no id
     */
    public MenuManager(String text, String id) {
        this(text, null, id);
    }

    /**
     * Creates a menu manager with the given text, image, and id.
     * Typically used for creating a sub-menu, where it needs to be referred to by id.
     *
     * @param text the text for the menu, or <code>null</code> if none
     * @param image the image for the menu, or <code>null</code> if none
     * @param id the menu id, or <code>null</code> if it is to have no id
     * @since 3.4
     */
    public MenuManager(String text, ImageDescriptor image, String id) {
        this.menuText = text;
        this.image = image;
        this.id = id;
    }

    @Override
