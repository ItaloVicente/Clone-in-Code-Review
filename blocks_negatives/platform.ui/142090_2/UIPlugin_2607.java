    /**
     * Returns the default instance of the receiver. This represents the runtime plugin.
     *
     * @return UIPlugin the singleton instance of the receiver.
     * @see AbstractUIPlugin for the typical implementation pattern for plugin classes.
     */
    public static UIPlugin getDefault() {
        return inst;
    }
