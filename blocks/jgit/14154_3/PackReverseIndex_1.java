		bucketSize = maxOffset / cnt + 1;
		int[] bucketIndex = new int[(int) cnt];
		int[] bucketValues = new int[(int) cnt + 1];
		for (int oi = 0; oi < offsetsBySha1.length; oi++) {
			final long o = offsetsBySha1[oi];
			final int bucket = (int) (o / bucketSize);
			final int bucketValuesPos = oi + 1;
			final int current = bucketIndex[bucket];
			bucketIndex[bucket] = bucketValuesPos;
			bucketValues[bucketValuesPos] = current;
		}
