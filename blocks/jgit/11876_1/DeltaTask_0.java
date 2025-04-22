
		void partitionTasks() {
			computeTopPaths();

			int topIdx = 0;
			long weightPerThread = totalWeight / threads;
			for (int i = beginIndex; i < endIndex;) {
				DeltaTask task = new DeltaTask(this);

				if (topIdx < topPathCnt)
					task.add(topPaths[topIdx++].slice());

				int s = i;
				long w = 0;
				for (; w < weightPerThread && i < endIndex; i++) {
					if (s < i && topPathAt(i)) {
						task.add(new Slice(s
						s = endOfTopPath();
					}
					w += list[i].getWeight();
				}

				if (s < i) {
					int h = list[i - 1].getPathHash();
					while (i < endIndex) {
						if (h == list[i].getPathHash())
							i++;
						else
							break;
					}
					task.add(new Slice(s
				}
				if (!task.slices.isEmpty())
					tasks.add(task);
			}

			topPaths = null;
		}

		private void computeTopPaths() {
			int cp = beginIndex;
			int ch = list[cp].getPathHash();
			long cw = list[cp].getWeight();
			totalWeight = list[cp].getWeight();

			for (int i = cp + 1; i < endIndex; i++) {
				ObjectToPack o = list[i];
				if (ch != o.getPathHash()) {
					if (MIN_TOP_PATH < cw) {
						if (topPathCnt < topPaths.length) {
							TopPath p = new TopPath(cw
							topPaths[topPathCnt] = p;
							if (++topPathCnt == topPaths.length)
								Arrays.sort(topPaths);
						} else if (topPaths[0].weight < cw) {
							topPaths[0] = new TopPath(cw
							if (topPaths[0].compareTo(topPaths[1]) > 0)
								Arrays.sort(topPaths);
						}
					}
					cp = i;
					ch = o.getPathHash();
					cw = 0;
				}
				cw += o.getWeight();
				totalWeight += o.getWeight();
			}

			Arrays.sort(topPaths
				public int compare(TopPath a
					return a.beginIdx - b.beginIdx;
				}
			});

			for (int i = 0; i < topPathCnt; i++)
				totalWeight -= topPaths[i].weight;
		}

		private boolean topPathAt(int i) {
			return nextTopPath < topPathCnt
					&& i == topPaths[nextTopPath].beginIdx;
		}

		private int endOfTopPath() {
			return topPaths[nextTopPath++].endIdx;
		}
	}

	static final class TopPath implements Comparable<TopPath> {
		final long weight;
		final int pathHash;
		final int beginIdx;
		final int endIdx;

		TopPath(long weight
			this.weight = weight;
			this.pathHash = pathHash;
			this.beginIdx = beginIdx;
			this.endIdx = endIdx;
		}

		public int compareTo(TopPath o) {
			int cmp = Long.signum(weight - o.weight);
			if (cmp != 0)
				return cmp;
			cmp = (pathHash >>> 1) - (o.pathHash >>> 1);
			if (cmp != 0)
				return cmp;
			return (pathHash & 1) - (pathHash & 1);
		}

		Slice slice() {
			return new Slice(beginIdx
		}
