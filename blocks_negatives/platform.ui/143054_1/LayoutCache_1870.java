    /**
     * Sets the controls that are being cached here. If these are the same
     * controls that were used last time, this method does nothing. Otherwise,
     * the cache is flushed and a new cache is created for the new controls.
     *
     * @param controls
     */
    public void setControls(Control[] controls) {
        if (controls.length != caches.length) {
            rebuildCache(controls);
            return;
        }
