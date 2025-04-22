
		void partitionTasks() {
			ArrayList<TopPath> topPaths = computeTopPaths();
			Iterator<TopPath> topPathItr = topPaths.iterator();
			int nextTop = 0;
			long weightPerThread = totalWeight / threads;
			for (int i = beginIndex; i < endIndex;) {
				DeltaTask task = new DeltaTask(this);
				long w = 0;

				if (topPathItr.hasNext()) {
					TopPath p = topPathItr.next();
					w += p.weight;
					task.add(p.slice);
				}

				int s = i;
				for (; w < weightPerThread && i < endIndex;) {
					if (nextTop < topPaths.size()
							&& i == topPaths.get(nextTop).slice.beginIndex) {
						if (s < i)
							task.add(new Slice(s
						s = i = topPaths.get(nextTop++).slice.endIndex;
					} else
						w += list[i++].getWeight();
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
			while (topPathItr.hasNext()) {
				TopPath p = topPathItr.next();
				DeltaTask task = new DeltaTask(this);
				task.add(p.slice);
				tasks.add(task);
			}

			topPaths = null;
		}

		private ArrayList<TopPath> computeTopPaths() {
			ArrayList<TopPath> topPaths = new ArrayList<TopPath>(threads);
			int cp = beginIndex;
			int ch = list[cp].getPathHash();
			long cw = list[cp].getWeight();
			totalWeight = list[cp].getWeight();

			for (int i = cp + 1; i < endIndex; i++) {
				ObjectToPack o = list[i];
				if (ch != o.getPathHash()) {
					if (MIN_TOP_PATH < cw) {
						if (topPaths.size() < threads) {
							TopPath p = new TopPath(cw
							topPaths.add(p);
							if (topPaths.size() == threads)
								Collections.sort(topPaths);
						} else if (topPaths.get(0).weight < cw) {
							TopPath p = new TopPath(cw
							topPaths.set(0
							if (p.compareTo(topPaths.get(1)) > 0)
								Collections.sort(topPaths);
						}
					}
					cp = i;
					ch = o.getPathHash();
					cw = 0;
				}
				if (o.isEdge() || o.doNotAttemptDelta())
					continue;
				cw += o.getWeight();
				totalWeight += o.getWeight();
			}

			Collections.sort(topPaths
				public int compare(TopPath a
					return a.slice.beginIndex - b.slice.beginIndex;
				}
			});
			return topPaths;
		}
	}

	static final class TopPath implements Comparable<TopPath> {
		final long weight;
		final int pathHash;
		final Slice slice;

		TopPath(long weight
			this.weight = weight;
			this.pathHash = pathHash;
			this.slice = s;
		}

		public int compareTo(TopPath o) {
			int cmp = Long.signum(weight - o.weight);
			if (cmp != 0)
				return cmp;
			return slice.beginIndex - o.slice.beginIndex;
		}
