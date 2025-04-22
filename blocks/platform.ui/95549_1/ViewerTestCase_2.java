		Policy.setLog(status -> fail(status.getMessage()));
		SafeRunnable.setRunner(code -> {
			try {
				code.run();
			} catch (Throwable th) {
				throw new RuntimeException(th);
			}
		});
