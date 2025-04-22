	private static final class HashEntry {
		/** Next entry in the hash table's chain list. */
		final HashEntry next;

		/** The referenced object. */
		final Ref ref;

		HashEntry(HashEntry n, Ref r) {
			next = n;
			ref = r;
		}
	}

	static final class Ref<T> {
		final DfsPackKey pack;
		final long position;
		final int size;
		volatile T value;
		Ref next;
		volatile boolean hot;

		Ref(DfsPackKey pack, long position, int size, T v) {
			this.pack = pack;
			this.position = position;
			this.size = size;
			this.value = v;
		}

		T get() {
			T v = value;
			if (v != null)
				hot = true;
			return v;
		}

		boolean has() {
			return value != null;
		}
	}
}
