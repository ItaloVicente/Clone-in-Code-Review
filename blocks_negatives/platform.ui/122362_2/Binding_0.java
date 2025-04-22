
	/**
	 * @param observable the observable that is checked for disposal before the
	 * runnable gets executed.
	 * @param r the Runnable to execute in the observable's realm.
	 * @since 1.6
	 */
	protected final void execAfterDisposalCheck(final IObservable observable, final Runnable r) {
		observable.getRealm().exec(() -> {
			if (!observable.isDisposed()) {
				r.run();
			}
		});
	}
