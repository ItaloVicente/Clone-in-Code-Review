		int totalCommits = 0;
		for (BitmapBuilder bitmapableCommits : result.paths)
			totalCommits += bitmapableCommits.cardinality();

		if (totalCommits == 0)
			return Collections.emptyList();
