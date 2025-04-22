    protected void clearState() {
    	this.currentTarget = null;
    }

    /**
     * Returns the position of the given event's coordinates relative to its target.
     * The position is determined to be before, after, or on the item, based on
     * some threshold value.
     *
     * @param event the event
     * @return one of the <code>LOCATION_* </code>constants defined in this class
     */
    protected int determineLocation(DropTargetEvent event) {
        if (!(event.item instanceof Item)) {
            return LOCATION_NONE;
        }
        Item item = (Item) event.item;
        Point coordinates = new Point(event.x, event.y);
        coordinates = viewer.getControl().toControl(coordinates);
