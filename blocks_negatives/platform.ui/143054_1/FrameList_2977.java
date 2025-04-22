            ((IPropertyChangeListener) listener).propertyChange(event);
        }
    }

    /**
     * Moves the frame pointer forward by one.
     * Has no effect if there is no frame after the current one.
     * Fires a <code>P_CURRENT_FRAME</code> property change event.
     */
    public void forward() {
        if (current < frames.size() - 1) {
            setCurrent(current + 1);
        }
    }

    /**
     * Returns the current frame.
     * Returns <code>null</code> if there is no current frame.
     *
     * @return the current frame, or <code>null</code>
     */
    public Frame getCurrentFrame() {
        return getFrame(current);
    }

    /**
     * Returns the index of the current frame.
     *
     * @return the index of the current frame
     */
    public int getCurrentIndex() {
        return current;
    }

    /**
     * Returns the frame at the given index, or <code>null</code>
     * if the index is &le; 0 or &ge; <code>size()</code>.
     *
     * @param index the index of the requested frame
     * @return the frame at the given index or <code>null</code>
     */
    public Frame getFrame(int index) {
        if (index < 0 || index >= frames.size()) {
