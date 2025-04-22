		if (cnt == 0) {
			bucketSize = Long.MAX_VALUE;
			offsetIndex = new int[1];
			nth = new int[0];
			return;
		}

		final long[] offsetsBySha1 = new long[(int) cnt];
