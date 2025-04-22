	public void setTopPriorityDirection(int direction) {
		if (direction == ASCENDING || direction == DESCENDING) {
			directions[priorities[0]] = direction;
		}
	}

	public int getTopPriorityDirection() {
		return directions[priorities[0]];
	}

	public int getTopPriority() {
		return priorities[0];
	}

	public int[] getPriorities() {
		return priorities;
	}

	public void resetState() {
		priorities = new int[DEFAULT_PRIORITIES.length];
		System.arraycopy(DEFAULT_PRIORITIES, 0, priorities, 0,
				priorities.length);
		directions = new int[DEFAULT_DIRECTIONS.length];
		System.arraycopy(DEFAULT_DIRECTIONS, 0, directions, 0,
				directions.length);
	}

	private int compare(IMarker marker1, IMarker marker2, int depth) {
		if (depth >= priorities.length) {
