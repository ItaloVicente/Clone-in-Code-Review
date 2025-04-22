			for (int i = 0; i < commit.mergingLanes.length; i++) {
				final TLane pLane = (TLane) commit.mergingLanes[i];
				final TColor pColor = laneColor(pLane);
				final int cx = laneC(pLane);

				if (Math.abs(myLaneX - cx) > LANE_WIDTH) {
					final int ix;
					if (myLaneX < cx)
						ix = cx - LANE_WIDTH / 2;
					else
						ix = cx + LANE_WIDTH / 2;

					drawLine(pColor, myLaneX, h / 2, ix, h / 2, LINE_WIDTH);
					drawLine(pColor, ix, h / 2, cx, h, LINE_WIDTH);
				} else
					drawLine(pColor, myLaneX, h / 2, cx, h, LINE_WIDTH);

				maxCenter = Math.max(maxCenter, cx);
			}
