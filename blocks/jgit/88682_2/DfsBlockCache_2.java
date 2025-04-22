		removeRef(pack.key);
	}

	@SuppressWarnings("unchecked")
	private void removeRef(DfsPackKey key) {
		clockLock.lock();
		try {
			Ref prev = clockHand;
			Ref hand = clockHand.next;
			Ref last = null;
			long live = liveBytes;
			while (last != clockHand) {
				if (prev == hand) {
					break;
				}
				last = hand;
				if (hand.pack == key) {
					Ref dead = hand;
					hand = hand.next;
					prev.next = hand;
					dead.next = null;
					dead.value = null;
					live -= dead.size;
					dead.pack.cachedSize.addAndGet(-dead.size);

					int slot = slot(dead.pack
					for (HashEntry entry = table
							.get(slot); entry != null; entry = entry.next) {
						if (entry.ref == dead) {
							table.compareAndSet(slot
							break;
						}
					}

					statEvict++;
				} else {
					prev = hand;
					hand = prev.next;
				}
			}
			liveBytes = live;
			clockHand = prev;
		} finally {
			clockLock.unlock();
		}
