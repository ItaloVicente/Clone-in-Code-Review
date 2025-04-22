	private static final Object LOCK = new Object();

	private static volatile RepositoryUtil instance;

	public static RepositoryUtil getInstance() {
		RepositoryUtil util = instance;
		if (util == null) {
			boolean interrupted = false;
			synchronized (LOCK) {
				util = instance;
				while (util == null) {
					try {
						LOCK.wait();
					} catch (InterruptedException e) {
						interrupted = true;
					}
					util = instance;
				}
			}
			if (interrupted) {
				Thread.currentThread().interrupt();
			}
		}
		return util;
	}

	static void create(IPath workspaceLocation) {
		synchronized (LOCK) {
			instance = new RepositoryUtil(workspaceLocation);
			LOCK.notifyAll();
		}
	}

