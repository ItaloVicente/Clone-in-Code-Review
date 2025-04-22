				System.out.println("cannot steal");
			}
		}

		void partitionTasks() {
			computeTopPaths();

			int topIdx = 0;
			long weightPerThread = totalWeight / threads;
			for (int i = beginIndex; i < endIndex;) {
				DeltaTask task = new DeltaTask(this

				if (topIdx < topPathCnt) {
					TopPath p = topPaths[topIdx++];
					task.add(p.slice());
				}

				int s = i;
				long w = 0;
				for (; w < weightPerThread && i < endIndex;) {
					if (topPathAt(i)) {
						if (s < i)
							task.add(new Slice(s
						s = i = endOfTopPath();
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
System.out.println(String.format("%d starts with %d slices
				if (!task.slices.isEmpty())
					tasks.add(task);
			}
			for (;topIdx < topPathCnt; topIdx++) {
				DeltaTask task = new DeltaTask(this
				TopPath p = topPaths[topIdx++];
				System.out.println(String.format(
						"%d starts with %d slices
						task.slices.size()
				task.add(p.slice());
				tasks.add(task);
