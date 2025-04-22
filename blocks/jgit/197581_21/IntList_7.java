	public void sortUsingComparator(Comparator<Integer> comparator) {
		quickSort(0
	}

	private void quickSort(int begin
		if (begin < end) {
			int partitionIndex = partition(begin

			quickSort(begin
			quickSort(partitionIndex + 1
		}
	}

	private int partition(int begin
		int pivot = entries[end];
		int writeSmallerIdx = (begin - 1);

		for (int findSmallerIdx = begin; findSmallerIdx < end; findSmallerIdx++) {
			if (comparator.compare(entries[findSmallerIdx]
				writeSmallerIdx++;

				int biggerVal = entries[writeSmallerIdx];
				entries[writeSmallerIdx] = entries[findSmallerIdx];
				entries[findSmallerIdx] = biggerVal;
			}
		}

		int pivotIdx = writeSmallerIdx + 1;
		entries[end] = entries[pivotIdx];
		entries[pivotIdx] = pivot;

		return pivotIdx;
	}

