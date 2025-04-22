
		final synchronized boolean canClear() {
			if (cleared)
				return false;
			cleared = true;
			return true;
		}
