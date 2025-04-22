    /**
     * Adds a drill frame to the stack.
     *
     * @param oRecord the new drill frame
     */
    public DrillFrame add(DrillFrame oRecord) {
        fStack.push(oRecord);
        return oRecord;
    }
