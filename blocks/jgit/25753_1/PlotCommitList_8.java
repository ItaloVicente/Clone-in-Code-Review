			if (reservedLane != null) {
				currCommit.lane = reservedLane;
				laneLength.put(reservedLane
						Integer.valueOf(lengthOfReservedLane + 1));
				handleBlockedLanes(index
			} else {
				currCommit.lane = nextFreeLane();
				handleBlockedLanes(index
			}
