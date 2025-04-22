		if (cnt == 0)
			lanes = new PlotLane[] { l };
		else if (cnt == 1)
			lanes = new PlotLane[] { lanes[0], l };
		else {
			final PlotLane[] n = new PlotLane[cnt + 1];
			System.arraycopy(lanes, 0, n, 0, cnt);
			n[cnt] = l;
			lanes = n;
		}
