		try {
			if (credit != 0)
				liveBytes -= credit;
			Ref ptr = clockHand;
			ref.next = ptr.next;
			ptr.next = ref;
			clockHand = ref;
		} finally {
			clockLock.unlock();
		}
