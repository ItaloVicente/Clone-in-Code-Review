			entry = entry.next;
		}
		return null;
	}

	private int hashCode(Object key) {
		if (comparer == null) {
