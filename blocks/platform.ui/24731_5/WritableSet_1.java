
	private static class FakeSet<E> extends ArrayList<E> implements Set<E> {
		private static final long serialVersionUID = 1L;

		private FakeSet(Collection<E> items) {
			super(items);
		}
	}
