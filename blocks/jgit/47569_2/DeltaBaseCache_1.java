		if (e != lruHead) {
			lruRemove(e);
			lruPushHead(e);
		}
	}

	private void lruRemove(final Slot e) {
		Slot p = e.lruPrev;
		Slot n = e.lruNext;

		if (p != null) {
			p.lruNext = n;
		} else {
			lruHead = n;
		}

		if (n != null) {
			n.lruPrev = p;
		} else {
			lruTail = p;
		}
	}

	private void lruPushHead(final Slot e) {
		Slot n = lruHead;
		e.lruNext = n;
		if (n != null)
			n.lruPrev = e;
