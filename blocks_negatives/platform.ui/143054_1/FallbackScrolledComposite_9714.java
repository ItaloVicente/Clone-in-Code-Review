    /**
     * Recomputes the body layout and the scroll bars. The method should be used
     * when changes somewhere in the form body invalidate the current layout
     * and/or scroll bars.
     *
     * @param flushCache
     *            if <code>true</code>, drop the cached data
     */
    public void reflow(boolean flushCache) {
        Composite c = (Composite) getContent();
        Rectangle clientArea = getClientArea();
        if (c == null)
            return;
