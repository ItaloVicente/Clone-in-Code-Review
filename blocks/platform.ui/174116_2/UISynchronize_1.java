
	protected abstract boolean isUIThread(Thread thread);

	protected abstract void showBusyWhile(Runnable runnable);

	protected abstract boolean dispatchEvents();

	public void exec(Runnable action) {
		Thread thread = Thread.currentThread();
		if (isUIThread(thread)) {
			exec(action);
		} else {
			syncExec(action);
		}
	}

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

	public <T> T busyCall(Callable<T> action)
			throws InterruptedException, ExecutionException {
		Objects.requireNonNull(action);
		FutureTask<T> task = new FutureTask<>(action);
		Thread thread = Thread.currentThread();
		if (isUIThread(thread)) {
			ForkJoinTask<?> fork = ForkJoinPool.commonPool().submit(task);
			showBusyWhile(() -> {
				while (!fork.isDone() && !thread.isInterrupted()) {
					if (dispatchEvents()) {
						continue;
					}
					Thread.yield();
				}
			});
			if (thread.isInterrupted()) {
				throw new InterruptedException();
			}
		} else {
			task.run();
		}
		return task.get();
	}
