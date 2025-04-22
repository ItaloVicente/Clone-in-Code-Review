	protected void clearState() {
		this.currentTarget = null;
	}

	protected int determineLocation(DropTargetEvent event) {
		if (!(event.item instanceof Item)) {
			return LOCATION_NONE;
		}
		Item item = (Item) event.item;
		Point coordinates = new Point(event.x, event.y);
		coordinates = viewer.getControl().toControl(coordinates);
