
	private static class Closer extends Thread {

		private final ReferenceQueue<RepositoryHandle> queue;

		public Closer(ReferenceQueue<RepositoryHandle> queue) {
			this.queue = queue;
			setDaemon(true);
			setName("Git Repository Closer"); //$NON-NLS-1$
		}

		@Override
		public void run() {
			try {
				for (;;) {
					closeReference(queue.remove());
				}
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}

		public static void closeReference(
				Reference<? extends RepositoryHandle> stale) {
			if (stale instanceof RepositoryReference) {
				RepositoryReference ref = (RepositoryReference) stale;
				Repository repository = ref.getRepository();
				if (repository != null) {
					repository.close();
				}
				ref.clearRepository();
			}
		}
	}
