		for (;;) {
			final Block<V> b = dirTable.get(hash(newValue));
			final V old = b.addIfAbsent(newValue);
			if (old == newValue) {
				size++;
				return newValue;
			}
