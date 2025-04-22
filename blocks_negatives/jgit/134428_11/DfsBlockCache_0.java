	<T> Ref<T> getRef(DfsStreamKey key) {
		Ref<T> r = scanRef(table.get(slot(key, 0)), key, 0);
		if (r != null)
			getStat(statHit, key).incrementAndGet();
		else
			getStat(statMiss, key).incrementAndGet();
		return r;
	}

