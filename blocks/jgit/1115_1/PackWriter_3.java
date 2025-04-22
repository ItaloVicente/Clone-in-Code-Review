	private void searchForDeltas(final ProgressMonitor monitor
			final ObjectToPack[] list
			throws MissingObjectException
			LargeObjectException
		if (threads == 0)
			threads = Runtime.getRuntime().availableProcessors();

		if (threads <= 1 || cnt <= 2 * getDeltaSearchWindowSize()) {
			DeltaCache dc = new DeltaCache(this);
			DeltaWindow dw = new DeltaWindow(this
			dw.search(monitor
			return;
		}

		final List<Throwable> errors = Collections
				.synchronizedList(new ArrayList<Throwable>());
		final DeltaCache dc = new ThreadSafeDeltaCache(this);
		final ProgressMonitor pm = new ThreadSafeProgressMonitor(monitor);
		final ExecutorService pool = Executors.newFixedThreadPool(threads);

		int estSize = cnt / (threads * 2);
		if (estSize < 2 * getDeltaSearchWindowSize())
			estSize = 2 * getDeltaSearchWindowSize();

		for (int i = 0; i < cnt;) {
			final int start = i;
			final int batchSize;

			if (cnt - i < estSize) {
				batchSize = cnt - i;
			} else {
				int end = start + estSize;
				while (end < cnt) {
					ObjectToPack a = list[end - 1];
					ObjectToPack b = list[end];
					if (a.getPathHash() == b.getPathHash())
						end++;
					else
						break;
				}
				batchSize = end - start;
			}
			i += batchSize;

			pool.submit(new Runnable() {
				public void run() {
					try {
						final ObjectReader or = reader.newReader();
						try {
							DeltaWindow dw;
							dw = new DeltaWindow(PackWriter.this
							dw.search(pm
						} finally {
							or.release();
						}
					} catch (Throwable err) {
						errors.add(err);
					}
				}
			});
		}

		pool.shutdown();
		for (;;) {
			try {
				if (pool.awaitTermination(60
					break;
			} catch (InterruptedException e) {
				throw new IOException(
						JGitText.get().packingCancelledDuringObjectsWriting);
			}
		}

		if (!errors.isEmpty()) {
			Throwable err = errors.get(0);
			if (err instanceof Error)
				throw (Error) err;
			if (err instanceof RuntimeException)
				throw (RuntimeException) err;
			if (err instanceof IOException)
				throw (IOException) err;

			IOException fail = new IOException(err.getMessage());
			fail.initCause(err);
			throw fail;
		}
