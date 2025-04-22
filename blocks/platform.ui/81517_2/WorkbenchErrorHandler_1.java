	private ExecutorService uiCheckService = Executors.newFixedThreadPool(1);

	private boolean isSafeToAccessUiThread() {
		Future<?> future = uiCheckService.submit(() -> {
			Display.getDefault().syncExec(() -> {
			});
		});
		try {
			future.get(5, TimeUnit.SECONDS);
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			return false;
		}
		return true;
	}

