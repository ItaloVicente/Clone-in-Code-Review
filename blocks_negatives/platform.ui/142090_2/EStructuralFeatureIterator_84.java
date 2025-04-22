    /**
     * Set position for an individual property.
     * @param position int position
     * @return whether this was a valid position
     */
    private boolean setPositionIndividualProperty(int position) {
        this.position = position;
        if (position < 1) {
            return false;
        }
