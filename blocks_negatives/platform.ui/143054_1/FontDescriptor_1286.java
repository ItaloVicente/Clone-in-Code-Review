    /**
     * Deallocates anything that was allocated by createFont, given a font
     * that was allocated by an equal FontDescriptor.
     *
     * @since 3.1
     *
     * @param previouslyCreatedFont previously allocated font
     */
    public abstract void destroyFont(Font previouslyCreatedFont);
