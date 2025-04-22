    /**
     * Returns this window manager's set of windows.
     *
     * @return a possibly empty list of window
     */
    public Window[] getWindows() {
        Window bs[] = new Window[windows.size()];
        windows.toArray(bs);
        return bs;
    }
