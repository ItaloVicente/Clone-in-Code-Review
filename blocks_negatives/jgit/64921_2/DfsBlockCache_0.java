		int eb = (int) (tableSize * .1);
		if (64 < eb)
			eb = 64;
		else if (eb < 4)
			eb = 4;
		if (tableSize < eb)
			eb = tableSize;

