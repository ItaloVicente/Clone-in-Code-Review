		List<Future<?>> futures;
		synchronized(tb) {
			futures = new ArrayList<Future<?>>(tb.tasks.size());
			for (DeltaTask task : tb.tasks) {
				futures.add(pool.submit(task));
			}
		}
