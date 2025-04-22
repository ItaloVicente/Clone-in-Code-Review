	private static long pair(int key
		if (MAX_COUNT < cnt)
			throw new TableFullException();
		return (((long) key) << KEY_SHIFT) | cnt;
	}

