				if (c.lane == null) {
					c.lane = nextFreeLane();
					if (reserverdLane != null) {
						recycleLane((L) c.lane);
						freePositions
								.add(Integer.valueOf(c.lane.getPosition()));
					} else {
						reserverdLane = c.lane;
					}
				} else {
					if (reserverdLane == null && activeLanes.contains(c.lane)) {
						reserverdLane = c.lane;
					} else {
						if (activeLanes.remove(c.lane)) {
							recycleLane((L) c.lane);
							freePositions.add(Integer.valueOf(c.lane
									.getPosition()));
						}
					}
