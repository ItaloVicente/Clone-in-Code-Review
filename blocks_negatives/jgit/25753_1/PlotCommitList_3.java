			for (int r = index - 1; r >= 0; r--) {
				final PlotCommit rObj = get(r);
				if (rObj == c)
					break;
				rObj.addPassingLane(c.lane);
			}
