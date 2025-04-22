
	static final void execAfterDisposalCheck(final IObservable observable, final Runnable runnable) {
		observable.getRealm().exec(() -> {
			if (!observable.isDisposed()) {
				runnable.run();
			}
		});
	}
