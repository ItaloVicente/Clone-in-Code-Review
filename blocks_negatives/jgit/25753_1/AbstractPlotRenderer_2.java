				} else {
					final int ix = cx + LANE_WIDTH / 2;
					drawLine(pColor, myLaneX, h / 2, ix, h / 2, LINE_WIDTH);
					drawLine(pColor, ix, h / 2, cx, h, LINE_WIDTH);
				}
			} else {
				drawLine(pColor, myLaneX, h / 2, cx, h, LINE_WIDTH);
