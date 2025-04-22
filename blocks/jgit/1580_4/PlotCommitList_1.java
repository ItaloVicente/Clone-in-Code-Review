				if (c.lane == null) {
					c.lane = nextFreeLane();
					activeLanes.add(c.lane);
					if (reservedLane != null)
						closeLane(c.lane);
					else
						reservedLane = c.lane;
				} else if (reservedLane == null && activeLanes.contains(c.lane))
					reservedLane = c.lane;
				else
					closeLane(c.lane);
