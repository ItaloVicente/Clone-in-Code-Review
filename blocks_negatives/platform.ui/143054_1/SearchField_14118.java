	/**
	 * This method was copy/pasted from JFace.
	 */
	private static Monitor getClosestMonitor(Display toSearch, Point toFind) {
		int closest = Integer.MAX_VALUE;

		Monitor[] monitors = toSearch.getMonitors();
		Monitor result = monitors[0];

		for (Monitor currentMonitor : monitors) {
			Rectangle clientArea = currentMonitor.getClientArea();

			if (clientArea.contains(toFind)) {
				return currentMonitor;
			}

			int distance = Geometry.distanceSquared(Geometry.centerPoint(clientArea), toFind);
			if (distance < closest) {
				closest = distance;
				result = currentMonitor;
			}
		}

		return result;
	}

