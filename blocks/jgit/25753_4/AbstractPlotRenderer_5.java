				if (Math.abs(myLaneX - cx) > LANE_WIDTH) {
					final int ix;
					if (myLaneX < cx)
						ix = cx - LANE_WIDTH / 2;
					else
						ix = cx + LANE_WIDTH / 2;
