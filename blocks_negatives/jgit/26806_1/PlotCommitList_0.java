				if (c.lane == null) {
					c.lane = nextFreeLane();
					if (reservedLane != null)
						closeLane(c.lane);
					else
						reservedLane = c.lane;
				} else if (reservedLane == null && activeLanes.contains(c.lane))
