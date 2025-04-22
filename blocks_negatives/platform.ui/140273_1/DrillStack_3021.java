    /**
     * Navigate to the home record.
     */
    public DrillFrame goHome() {
        DrillFrame aFrame = (DrillFrame) fStack.elementAt(0);
        reset();
        return aFrame;
    }
