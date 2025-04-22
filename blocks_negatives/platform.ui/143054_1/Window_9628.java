	/**
	 * Returns the monitor whose client area contains the given point. If no
	 * monitor contains the point, returns the monitor that is closest to the
	 * point. If this is ever made public, it should be moved into a separate
	 * utility class.
	 *
	 * @param toSearch
	 *            point to find (display coordinates)
	 * @param toFind
	 *            point to find (display coordinates)
	 * @return the monitor closest to the given point
	 */
	private static Monitor getClosestMonitor(Display toSearch, Point toFind) {
		int closest = Integer.MAX_VALUE;

		Monitor[] monitors = toSearch.getMonitors();
		Monitor result = monitors[0];

		for (Monitor current : monitors) {
			Rectangle clientArea = current.getClientArea();

			if (clientArea.contains(toFind)) {
				return current;
			}

			int distance = Geometry.distanceSquared(Geometry
					.centerPoint(clientArea), toFind);
			if (distance < closest) {
				closest = distance;
				result = current;
			}
		}

		return result;
	}
