    /**
     * Set position for all properties
     * @param position int position
     * @return whether this was a valid position
     */
    private boolean setPositionAllProperties(int position) {
        this.position = position;
        if (position < 1) {
            return false;
        }
