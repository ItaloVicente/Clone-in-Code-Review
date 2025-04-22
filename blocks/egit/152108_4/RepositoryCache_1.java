	private static class RepositoryReference
			extends WeakReference<RepositoryHandle> {

		private Repository inner;

		public RepositoryReference(RepositoryHandle handle, Repository delegate,
				ReferenceQueue<RepositoryHandle> queue) {
			super(handle, queue);
			inner = delegate;
		}

		public Repository getRepository() {
			return inner;
		}

		public void clearRepository() {
			inner = null;
		}
	}

