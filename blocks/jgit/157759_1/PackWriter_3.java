	private static final Iterable<PackWriter> instancesIterable = () -> new Iterator<PackWriter>() {

		private final Iterator<WeakReference<PackWriter>> it = instances
				.keySet().iterator();

		private PackWriter next;

