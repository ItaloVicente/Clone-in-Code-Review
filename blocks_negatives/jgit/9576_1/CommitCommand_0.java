		String p = pathString;
		while (true) {
			int position = Collections.binarySearch(only, p);
			if (position >= 0)
				return position;
			if (l < 1)
				break;
			p = p.substring(0, l);
