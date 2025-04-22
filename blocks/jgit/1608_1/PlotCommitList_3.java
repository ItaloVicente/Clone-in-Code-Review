	private void closeLane(PlotLane lane) {
		recycleLane((L) lane);
		if (activeLanes.remove(lane)) {
			freePositions.add(Integer.valueOf(lane.getPosition()));
		}
	}

