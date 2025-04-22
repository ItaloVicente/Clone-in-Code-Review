
	public int compareTo(Edit o) {
		if (o==null || endA<=o.beginA) {
			return -1;
		} else if (o.endA < beginA) {
			return 1;
		}
		return 0;
	}
