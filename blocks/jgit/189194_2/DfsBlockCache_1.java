	private void reportIndexRequested(Ref<?> ref
			long start) {
		if (indexEventConsumer == null
				|| !isIndexOrBitmapExtPos(ref.key.packExtPos)) {
			return;
		}
		EvictKey evictKey = new EvictKey(ref);
		Long prevEvictedTime = indexEvictionMap.get(evictKey);
		long now = System.nanoTime();
		long sinceLastEvictionNanos = prevEvictedTime == null ? 0L
				: now - prevEvictedTime.longValue();
		indexEventConsumer.acceptLoadedEvent(ref.key.packExtPos
				Duration.ofNanos(sinceLastEvictionNanos));
	}

	private void reportIndexEvicted(Ref<?> dead) {
		if (indexEventConsumer == null
				|| !indexEventConsumer.shouldReportEvictedEvent()
				|| !isIndexOrBitmapExtPos(dead.key.packExtPos)) {
			return;
		}
		EvictKey evictKey = new EvictKey(dead);
		Long prevEvictedTime = indexEvictionMap.get(evictKey);
		long now = System.nanoTime();
		long sinceLastEvictionNanos = prevEvictedTime == null ? 0L
				: now - prevEvictedTime.longValue();
		indexEvictionMap.put(evictKey
		indexEventConsumer.acceptEvictedEvent(dead.key.packExtPos
				dead.totalHitCount.get()
				Duration.ofNanos(sinceLastEvictionNanos));
	}

	private static boolean isIndexOrBitmapExtPos(int packExtPos) {
		return packExtPos == PackExt.INDEX.getPosition()
				|| packExtPos == PackExt.BITMAP_INDEX.getPosition();
	}

