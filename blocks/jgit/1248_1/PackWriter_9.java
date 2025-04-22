			myTasks.add(new DeltaTask(config
		}

		final Executor executor = config.getExecutor();
		final List<Throwable> errors = Collections
				.synchronizedList(new ArrayList<Throwable>());
		if (executor instanceof ExecutorService) {
			runTasks((ExecutorService) executor
