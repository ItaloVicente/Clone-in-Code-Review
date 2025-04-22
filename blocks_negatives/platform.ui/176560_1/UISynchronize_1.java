	/**
	 * Executes the given action inside the UI thread (either directly if the
	 * current thread is the UI-Thread or by using {@link #syncExec(Runnable)} if
	 * not returning the result to the caller
	 *
	 * @param action the action to perform
	 * @since 1.3
	 */
	public void exec(Runnable action) {
		Thread thread = Thread.currentThread();
		if (isUIThread(thread)) {
			action.run();
		} else {
			syncExec(action);
		}
	}

	/**
	 * Calls the given {@link Callable} inside the UI thread (either directly if the
	 * current thread is the UI Thread or by using {@link #syncExec(Runnable)} if
	 * not returning the result to the caller
	 *
	 * @param <T>    the return type of the {@link Callable}
	 * @param action the action to perform
	 * @return the value as a result of calling the {@link Callable}
	 * @throws InterruptedException if either the current or the background thread
	 *                              where interrupted while waiting for the result
	 * @throws ExecutionException   if the synchronous execution has thrown an
	 *                              exception
	 * @since 1.3
	 */
	public <T> T call(Callable<T> action) throws InterruptedException, ExecutionException {
		Thread thread = Thread.currentThread();
		if (isUIThread(thread)) {
			try {
				return action.call();
			} catch (Exception e) {
				if (e instanceof InterruptedException) {
					throw (InterruptedException) e;
				}
				throw new ExecutionException(e);
			}
		} else {
			FutureTask<T> task = new FutureTask<>(action);
			try {
				syncExec(task);
			} catch (RuntimeException e) {
				throw new ExecutionException(e);
			}
			return task.get();
		}
	}

	/**
	 * Performs the given action in a background (non-ui thread) showing a
	 * busy-indicator on a best-effort basis if called from the UI-Thread, otherwise
	 * simply executes the action
	 *
	 * @param action the action to be performed must not be <code>null</code>
	 * @throws InterruptedException if either the current or the background thread
	 *                              where interrupted while waiting for the result
	 *                              exception
	 * @since 1.3
	 */
	public void busyExec(Runnable action) throws InterruptedException {
		try {
			busyCall(() -> {
				action.run();
				return null;
			});
		} catch (ExecutionException e) {
			Throwable cause = e.getCause();
			if (cause instanceof RuntimeException) {
				throw (RuntimeException) cause;
			}
			throw new RuntimeException(e);
		}
	}

	/**
	 * Performs the given action in a non-ui thread showing a busy-indicator on a
	 * best-effort basis and returning the result of the {@link Callable} to the
	 * caller
	 *
	 * @param <T>    return type of the {@link Callable}
	 * @param action the action to be performed must not be <code>null</code>
	 * @return the value as a result of calling the {@link Callable}
	 * @throws InterruptedException if either the current or the background thread
	 *                              where interrupted while waiting for the result
	 * @throws ExecutionException   if the concurrent execution has thrown an
	 *                              exception
	 * @since 1.3
	 */
	public <T> T busyCall(Callable<T> action) throws InterruptedException, ExecutionException {
		Objects.requireNonNull(action);
		FutureTask<T> task = new FutureTask<>(action);
		Thread thread = Thread.currentThread();
		if (isUIThread(thread)) {
			ForkJoinTask<?> fork = ForkJoinPool.commonPool().submit(task);
			showBusyWhile(() -> {
				while (!fork.isDone() && !Thread.currentThread().isInterrupted()) {
					if (dispatchEvents()) {
						continue;
					}
					Thread.yield();
				}
			});
		} else {
			task.run();
		}
		return task.get();
	}
