		long bytesPerUnit = 1;
		while (DeltaTask.MAX_METER <= (totalWeight / bytesPerUnit))
			bytesPerUnit <<= 10;
		int cost = (int) (totalWeight / bytesPerUnit);
		if (totalWeight % bytesPerUnit != 0)
			cost++;
