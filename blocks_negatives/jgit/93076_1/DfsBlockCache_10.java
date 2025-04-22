	@SuppressWarnings("unchecked")
	<T> T get(DfsPackKey key, long position) {
		T val = (T) scan(table.get(slot(key, position)), key, position);
		if (val == null)
			statMiss.incrementAndGet();
		else
			statHit.incrementAndGet();
		return val;
	}
