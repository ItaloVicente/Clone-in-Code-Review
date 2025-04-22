	@SuppressWarnings("unchecked")
	private <T> Ref<T> scanRef(HashEntry n, DfsPackKey pack, long position) {
		for (; n != null; n = n.next) {
			Ref<T> r = n.ref;
			if (r.pack == pack && r.position == position)
				return r.get() != null ? r : null;
		}
		return null;
	}
