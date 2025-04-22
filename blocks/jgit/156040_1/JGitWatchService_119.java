	private boolean wsClose = false;

	private final Queue<WatchKey> events = new ConcurrentLinkedQueue<>();
	private final String fsName;
	private Consumer<JGitWatchService> notifyClose;

	public JGitWatchService(String fsName

		this.fsName = fsName;
		this.notifyClose = notifyClose;
	}

	@Override
	public WatchKey poll() throws ClosedWatchServiceException {
		return events.poll();
	}

	@Override
	public WatchKey poll(long timeout
		return events.poll();
	}

	@Override
	public synchronized WatchKey take() throws ClosedWatchServiceException
		while (true) {
			if (wsClose) {
				throw new ClosedWatchServiceException();
			} else if (events.size() > 0) {
				return events.poll();
			} else {
				try {
					this.wait();
				} catch (final java.lang.InterruptedException e) {
				}
			}
		}
	}

	public boolean isClose() {
		return wsClose;
	}

	@Override
	public synchronized void close() throws IOException {
		wsClose = true;
		notifyAll();
		notifyClose.accept(this);
	}

	synchronized void closeWithoutNotifyParent() {
		wsClose = true;
		notifyAll();
	}

	@Override
	public String toString() {
		return "WatchService{" + "FileSystem=" + fsName + '}';
	}

	public void publish(WatchKey wk) {
		events.add(wk);
	}
