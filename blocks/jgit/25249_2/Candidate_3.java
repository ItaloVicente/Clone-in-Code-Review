	int sourceEnd() {
		Region r = regionList;
		while (r != null) {
			Region n = r.next;
			if (n == null)
				return r.sourceStart + r.length;
			r = n;
		}
		return 0;
	}

