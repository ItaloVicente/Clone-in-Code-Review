	<T> Ref<T> getRef(DfsStreamKey key) {
		Ref<T> r = scanRef(table.get(slot(key
		if (r != null)
			statHit.incrementAndGet();
		else
			statMiss.incrementAndGet();
		return r;
	}

