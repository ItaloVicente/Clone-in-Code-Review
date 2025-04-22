	<T> Ref<T> put(DfsPackKey key, long pos, int size, T v) {
		int slot = slot(key, pos);
		HashEntry e1 = table.get(slot);
		Ref<T> ref = scanRef(e1, key, pos);
		if (ref != null)
			return ref;
