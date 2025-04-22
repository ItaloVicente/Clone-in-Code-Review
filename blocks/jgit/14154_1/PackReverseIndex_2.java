		int nthByOffset = 0;
		nth = new int[offsetsBySha1.length];
		for (int bi = 0; bi < offsetIndex.length; bi++) {
			final int start = nthByOffset;
			for (int vi = offsetIndex[bi]; vi > 0; vi = bucketValues[vi]) {
				final int nthBySha1 = vi - 1;
				final long o = offsetsBySha1[nthBySha1];
				int insertion = nthByOffset++;
				for (; start < insertion; insertion--) {
					if (o > offsetsBySha1[nth[insertion - 1]])
						break;
					nth[insertion] = nth[insertion - 1];
				}
				nth[insertion] = nthBySha1;
			}
			offsetIndex[bi] = nthByOffset;
