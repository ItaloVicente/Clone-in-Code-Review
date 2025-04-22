
	public static void safeExec(final IObservable observable, final Runnable r) {
		observable.getRealm().exec(() -> {
			if (!observable.isDisposed()) {
				r.run();
			}
		});
	}
