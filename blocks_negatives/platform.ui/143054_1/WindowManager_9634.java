    /**
     * Attempts to close all windows managed by this window manager,
     * as well as windows managed by any descendent window managers.
     *
     * @return <code>true</code> if all windows were sucessfully closed,
     * and <code>false</code> if any window refused to close
     */
    public boolean close() {
        Iterator<Window> e = t.iterator();
        while (e.hasNext()) {
            Window window = e.next();
            boolean closed = window.close();
            if (!closed) {
