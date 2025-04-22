				@SuppressWarnings("unchecked")
				final PlotCommit<L> c = currCommit.children[i];
				if (c.getParent(0) == currCommit) {
					Integer len = laneLength.get(c.lane);
					if (len.intValue() > lengthOfReservedLane) {
						reservedLane = c.lane;
						childOnReservedLane = c;
						lengthOfReservedLane = len.intValue();
					}
				}
