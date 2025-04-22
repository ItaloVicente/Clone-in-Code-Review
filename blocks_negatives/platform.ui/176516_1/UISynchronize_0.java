			FutureTask<T> task = new FutureTask<>(action);
			try {
				syncExec(task);
			} catch (RuntimeException e) {
				throw new ExecutionException(e);
			}
			return task.get();
