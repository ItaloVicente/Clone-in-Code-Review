		long live = liveBytes + reserve;
		if (maxBytes < live) {
			Ref prev = clockHand;
			Ref hand = clockHand.next;
			do {
				if (hand.hot) {
					hand.hot = false;
					prev = hand;
