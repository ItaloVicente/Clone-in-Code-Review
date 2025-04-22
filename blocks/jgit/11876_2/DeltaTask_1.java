
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

System.out.println(String.format("%d top paths:"
for(int i = 0; i < topPathCnt; i++)
System.out.println(String.format("  %8x %5d %5d"
		topPaths[i].endIdx - topPaths[i].beginIdx
		topPaths[i].weight));
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
			return beginIdx - o.beginIdx;
		}

		Slice slice() {
			return new Slice(beginIdx
