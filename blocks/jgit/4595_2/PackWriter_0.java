	private static final Map<WeakReference<PackWriter>
			new ConcurrentHashMap<WeakReference<PackWriter>

	private static final Iterable<PackWriter> instancesIterable = new Iterable<PackWriter>() {
		public Iterator<PackWriter> iterator() {
			return new Iterator<PackWriter>() {
				private final Iterator<WeakReference<PackWriter>> it =
						instances.keySet().iterator();
				private PackWriter next;

				public boolean hasNext() {
					if (next != null)
						return true;
					while (it.hasNext()) {
						WeakReference<PackWriter> ref = it.next();
						next = ref.get();
						if (next != null)
							return true;
						it.remove();
					}
					return false;
				}

				public PackWriter next() {
					if (hasNext()) {
						PackWriter result = next;
						next = null;
						return result;
					}
					throw new NoSuchElementException();
				}

				public void remove() {
					throw new UnsupportedOperationException();
				}
			};
		}
	};

	public static Iterable<PackWriter> getInstances() {
		return instancesIterable;
	}

