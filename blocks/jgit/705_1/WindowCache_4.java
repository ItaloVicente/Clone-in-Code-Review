
		final synchronized boolean canClear() {
			if (cleared)
				return false;
			cleared = true;
			return true;
		}
	}

	private static final class Lock {
