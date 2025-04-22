	private static final int MAX = 16;

	private final List<AccessEvent> events = new ArrayList<>();

	private final Semaphore active = new Semaphore(MAX);

	void clear() {
		try {
			for (;;) {
				try {
					active.acquire(MAX);
					break;
				} catch (InterruptedException e) {
					continue;
				}
			}

			synchronized (events) {
				events.clear();
			}
		} finally {
			active.release(MAX);
		}
	}

	List<AccessEvent> getEvents() {
		try {
			for (;;) {
				try {
					active.acquire(MAX);
					break;
				} catch (InterruptedException e) {
					continue;
				}
			}

			synchronized (events) {
				return events;
			}
		} finally {
			active.release(MAX);
		}
	}

	@Override
	public void handle(String target
			HttpServletRequest request
			throws IOException
		try {
			for (;;) {
				try {
					active.acquire();
					break;
				} catch (InterruptedException e) {
					continue;
				}
			}

			super.handle(target

			if (DispatcherType.REQUEST.equals(baseRequest.getDispatcherType()))
				log((Request) request

		} finally {
			active.release();
		}
	}

	private void log(Request request
		synchronized (events) {
			events.add(new AccessEvent(request
		}
	}
