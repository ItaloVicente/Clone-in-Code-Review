			int remaining = nChildren;
			BitSet blockedPositions = new BitSet();
			for (int r = index - 1; r >= 0; r--) {
				final PlotCommit rObj = get(r);
				if (currCommit.isChild(rObj)) {
					if (--remaining == 0)
						break;
				}
				if (rObj != null) {
					PlotLane lane = rObj.getLane();
					if (lane != null)
						blockedPositions.set(lane.getPosition());
					rObj.addPassingLane(currCommit.lane);
				}
