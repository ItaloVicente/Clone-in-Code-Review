
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
					Reference<?> stale = queue.remove();
					if (stale instanceof RepositoryReference) {
						closeReference((RepositoryReference) stale);
					}
				}
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}

		public static void closeReference(RepositoryReference stale) {
			Repository repository = stale.getRepository();
			if (repository != null) {
				repository.close();
			}
			stale.clearRepository();
		}
	}
