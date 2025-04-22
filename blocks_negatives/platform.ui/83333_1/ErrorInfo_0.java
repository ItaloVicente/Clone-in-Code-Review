	public int compareTo(Object arg0) {
		if (arg0 instanceof ErrorInfo) {
			long otherTimestamp = ((ErrorInfo) arg0).timestamp;
			if (timestamp < otherTimestamp) {
				return -1;
			} else if (timestamp > otherTimestamp) {
				return 1;
			} else {
				return 0;
			}
