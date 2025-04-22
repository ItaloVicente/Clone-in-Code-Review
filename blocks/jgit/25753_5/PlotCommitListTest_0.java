		public int getLanePos() {
			return current.getLane().position;
		}

		public CommitListAssert lanePos(Set<Integer> allowedPositions) {
			PlotLane lane = current.getLane();
			@SuppressWarnings("boxing")
			boolean found = allowedPositions.remove(lane.getPosition());
			assertTrue("Position of lane of commit #" + (nextIndex - 1)
					+ " not as expected. Expecting one of: " + allowedPositions + " Actual: "+ lane.getPosition()
			return this;
		}

