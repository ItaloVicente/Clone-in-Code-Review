
	protected final void execAfterDisposalCheck(final IObservable observable, final Runnable r) {
		observable.getRealm().exec(() -> {
			if (!observable.isDisposed()) {
				r.run();
			}
		});
	}
