    /**
     * Set the enablement for all actions.
     */
    public void enableActions(boolean b) {
        for (int nX = 0; nX < ACTION_COUNT; nX++) {
            actions[nX].setEnabled(b);
        }
    }
