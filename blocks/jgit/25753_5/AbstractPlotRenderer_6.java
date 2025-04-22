		if (commit.getChildCount() > 0) {
			for (int i = 0; i < commit.forkingOffLanes.length; i++) {
				@SuppressWarnings("unchecked")
				final TLane childLane = (TLane) commit.forkingOffLanes[i];
				final TColor cColor = laneColor(childLane);
				final int cx = laneC(childLane);
				if (Math.abs(myLaneX - cx) > LANE_WIDTH) {
					final int ix;
					if (myLaneX < cx)
						ix = cx - LANE_WIDTH / 2;
					else
						ix = cx + LANE_WIDTH / 2;

					drawLine(cColor
					drawLine(cColor
				} else {
					drawLine(cColor
				}
				maxCenter = Math.max(maxCenter
			}

			int nonForkingChildren = commit.getChildCount()
					- commit.forkingOffLanes.length;
			if (nonForkingChildren > 0)
						drawLine(myColor
		}
