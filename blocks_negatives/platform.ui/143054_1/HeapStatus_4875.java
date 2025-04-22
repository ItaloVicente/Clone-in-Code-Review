    }

    /**
     * Sets the mark to the current usedMem level.
     */
    private void setMark() {
        mark = usedMem;
        hasChanged = true;
        redraw();
    }

    /**
     * Clears the mark.
     */
    private void clearMark() {
        mark = -1;
        hasChanged = true;
        redraw();
    }

    private void gc() {
