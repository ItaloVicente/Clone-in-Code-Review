    /**
     * Return whether an intro is available. Note that this checks whether
     * there is an applicable intro part that could be instantiated and shown
     * to the user.
     * Use {@link #getIntro()} to discover whether an intro part has already
     * been created.
     *
     * @return <code>true</code> if there is an intro that could be shown, and
     * <code>false</code> if there is no intro
     */
    boolean hasIntro();
