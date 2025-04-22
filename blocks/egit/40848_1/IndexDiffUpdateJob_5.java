		}
	}

	@Override
	protected void canceling() {
		cleanup();
	}

	void cleanupAndCancel() {
		cleanup();
		cancel();
	}

	private void cleanup() {
		synchronized (lock) {
			files = new HashSet<String>();
			resources = new HashSet<IResource>();
		}
