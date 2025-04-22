	private void unlink(final Slot e) {
		Slot prev = e.lruPrev;
		Slot next = e.lruNext;

		if (prev != null)
			prev.lruNext = next;
		if (next != null)
			next.lruPrev = prev;
