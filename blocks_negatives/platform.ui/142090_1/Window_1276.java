    /**
     * Sets the default parent for modal Windows. This will be used to locate
     * the parent for any modal Window constructed with a null parent.
     *
     * @param provider shell provider that will be used to locate the parent shell
     * whenever a Window is created with a null parent
     * @since 3.1
     */
    public static void setDefaultModalParent(IShellProvider provider) {
        defaultModalParent = provider;
    }
