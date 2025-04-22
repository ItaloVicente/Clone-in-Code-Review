		try {
			long live = liveBytes + reserve;
			if (maxBytes < live) {
				Ref prev = clockHand;
				Ref hand = clockHand.next;
				do {
					if (hand.hot) {
						hand.hot = false;
						prev = hand;
						hand = hand.next;
						continue;
					} else if (prev == hand)
						break;

					Ref dead = hand;
