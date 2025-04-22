	private void checkLoadable(DeltaWindowEntry ent
		int tail = next(resSlot);
		while (maxMemory < loaded + need) {
			DeltaWindowEntry cur = window[tail];
			clear(cur);
			if (cur == ent)
				throw new LargeObjectException.ExceedsLimit(
						maxMemory
			tail = next(tail);
		}
	}

