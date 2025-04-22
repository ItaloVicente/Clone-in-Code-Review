					prev.next = hand;
					dead.next = null;
					dead.value = null;
					live -= dead.size;
					dead.pack.cachedSize.addAndGet(-dead.size);
					statEvict++;
				} while (maxBytes < live);
				clockHand = prev;
			}
			liveBytes = live;
		} finally {
			clockLock.unlock();
