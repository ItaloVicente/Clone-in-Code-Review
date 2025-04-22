
		void partitionTasks() {
			ArrayList<WeightedPath> topPaths = computeTopPaths();
			Iterator<WeightedPath> topPathItr = topPaths.iterator();
			int nextTop = 0;
			long weightPerThread = totalWeight / threads;
			for (int i = beginIndex; i < endIndex;) {
				DeltaTask task = new DeltaTask(this);
				long w = 0;

				if (topPathItr.hasNext()) {
					WeightedPath p = topPathItr.next();
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
				WeightedPath p = topPathItr.next();
				DeltaTask task = new DeltaTask(this);
				task.add(p.slice);
				tasks.add(task);
			}

			topPaths = null;
		}

		private ArrayList<WeightedPath> computeTopPaths() {
			ArrayList<WeightedPath> topPaths = new ArrayList<WeightedPath>(
					threads);
			int cp = beginIndex;
			int ch = list[cp].getPathHash();
			long cw = list[cp].getWeight();
			totalWeight = list[cp].getWeight();

			for (int i = cp + 1; i < endIndex; i++) {
				ObjectToPack o = list[i];
				if (ch != o.getPathHash()) {
					if (MIN_TOP_PATH < cw) {
						if (topPaths.size() < threads) {
							Slice s = new Slice(cp
							topPaths.add(new WeightedPath(cw
							if (topPaths.size() == threads)
								Collections.sort(topPaths);
						} else if (topPaths.get(0).weight < cw) {
							Slice s = new Slice(cp
							WeightedPath p = new WeightedPath(cw
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
				public int compare(WeightedPath a
					return a.slice.beginIndex - b.slice.beginIndex;
				}
			});
			return topPaths;
		}
	}

	static final class WeightedPath implements Comparable<WeightedPath> {
		final long weight;
		final Slice slice;

		WeightedPath(long weight
			this.weight = weight;
			this.slice = s;
		}

		public int compareTo(WeightedPath o) {
			int cmp = Long.signum(weight - o.weight);
			if (cmp != 0)
				return cmp;
			return slice.beginIndex - o.slice.beginIndex;
		}
