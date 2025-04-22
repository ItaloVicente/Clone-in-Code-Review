        updateStatusLine(sel);
        updateActionBars(sel);
        dragDetected = false;
    }

    /**
     * Handles a key press event from the viewer.
     * Delegates to the action group.
     *
     * @param event the key event
     * @since 2.0
     */
    protected void handleKeyPressed(KeyEvent event) {
        getActionGroup().handleKeyPressed(event);
    }

    /**
     * Handles a key release in the viewer.  Does nothing by default.
     *
     * @param event the key event
     * @since 2.0
     */
    protected void handleKeyReleased(KeyEvent event) {
    }

    @Override
