    /**
     * Navigate backwards one record.
     */
    public DrillFrame goBack() {
        DrillFrame aFrame = (DrillFrame) fStack.pop();
        return aFrame;
    }
