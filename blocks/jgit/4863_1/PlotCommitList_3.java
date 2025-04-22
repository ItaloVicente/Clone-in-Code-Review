	private void findLaneForChild(final PlotCommit c) {
		c.lane = createLane();
		BitSet occupied = new BitSet();
		for (PlotLane l : c.passingLanes)
			occupied.set(l.getPosition());
		for (int i = 0; i <= c.passingLanes.length; i++) {
			if (!occupied.get(i)) {
				c.lane.position = i;
				break;
			}
		}
	}

