	private void lruPushHead(Entry e) {
		Entry n = lruHead;
		e.lruNext = n;
		if (n != null)
			n.lruPrev = e;
		else
			lruTail = e;
