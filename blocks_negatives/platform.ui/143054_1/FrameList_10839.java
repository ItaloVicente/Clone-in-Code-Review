        return frames.get(index);
    }

    /**
     * Returns the frame source.
     */
    public IFrameSource getSource() {
        return source;
    }

    /**
     * Adds the given frame after the current frame,
     * and advances the pointer to the new frame.
     * Before doing so, updates the current frame, and removes any frames following the current frame.
     * Fires a <code>P_CURRENT_FRAME</code> property change event.
     *
     * @param frame the frame to add
     */
    public void gotoFrame(Frame frame) {
        for (int i = frames.size(); --i > current;) {
            frames.remove(i);
        }
        frame.setParent(this);
        int index = frames.size();
        frame.setIndex(index);
        frames.add(frame);
        setCurrent(index);
    }

    /**
     * Removes a property change listener.
     * Has no effect if an identical listener is not registered.
     *
     * @param listener a property change listener
     */
    public void removePropertyChangeListener(IPropertyChangeListener listener) {
        removeListenerObject(listener);
    }

    /**
     * Sets the current frame to the one with the given index.
     * Updates the old current frame, and fires a <code>P_CURRENT_FRAME</code> property change event
     * if the current frame changes.
     *
     * @param newCurrent the index of the frame
     */
    void setCurrent(int newCurrent) {
        Assert.isTrue(newCurrent >= 0 && newCurrent < frames.size());
        int oldCurrent = this.current;
        if (oldCurrent != newCurrent) {
            updateCurrentFrame();
            this.current = newCurrent;
            firePropertyChange(new PropertyChangeEvent(this, P_CURRENT_FRAME,
                    getFrame(oldCurrent), getFrame(newCurrent)));
        }
    }

    /**
     * Sets the current frame to the frame with the given index.
     * Fires a <code>P_CURRENT_FRAME</code> property change event
     * if the current frame changes.
     */
    public void setCurrentIndex(int index) {
        if (index != -1 && index != current) {
