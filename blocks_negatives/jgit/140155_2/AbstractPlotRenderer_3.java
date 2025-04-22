			for (int i = 0; i < commit.forkingOffLanes.length; i++) {
				final TLane childLane = (TLane) commit.forkingOffLanes[i];
				final TColor cColor = laneColor(childLane);
				final int cx = laneC(childLane);
				if (Math.abs(myLaneX - cx) > LANE_WIDTH) {
					final int ix;
					if (myLaneX < cx)
						ix = cx - LANE_WIDTH / 2;
					else
						ix = cx + LANE_WIDTH / 2;

					drawLine(cColor, myLaneX, h / 2, ix, h / 2, LINE_WIDTH);
					drawLine(cColor, ix, h / 2, cx, 0, LINE_WIDTH);
				} else {
					drawLine(cColor, myLaneX, h / 2, cx, 0, LINE_WIDTH);
				}
				maxCenter = Math.max(maxCenter, cx);
			}
