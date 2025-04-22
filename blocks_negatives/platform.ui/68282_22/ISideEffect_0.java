	 * Creates a new {@link ISideEffect} on the default {@link Realm} but does
	 * not run it immediately. Callers are responsible for invoking
	 * {@link #resume()} or {@link #resumeAndRunIfDirty()} when they want the
	 * runnable to begin executing.
	 *
	 * @param runnable
	 *            the runnable to execute. Must be idempotent.
	 * @return a newly-created {@link ISideEffect} which has not yet been
	 *         activated. Callers are responsible for calling {@link #dispose()}
	 *         on the result when it is no longer needed.
	 */
	static ISideEffect createPaused(Runnable runnable) {
		return new SideEffect(runnable);
	}

	/**
	 * Creates a new {@link ISideEffect} on the given Realm but does not
	 * activate it immediately. Callers are responsible for invoking
	 * {@link #resume()} when they want the runnable to begin executing.
	 *
	 * @param realm
	 *            the realm to execute
	 * @param runnable
	 *            the runnable to execute. Must be idempotent.
	 * @return a newly-created {@link ISideEffect} which has not yet been
	 *         activated. Callers are responsible for calling {@link #dispose()}
	 *         on the result when it is no longer needed.
	 */
	static ISideEffect createPaused(Realm realm, Runnable runnable) {
		return new SideEffect(realm, runnable);
	}

	/**
	 * Runs the given runnable once synchronously. The runnable will then run
	 * again after any tracked getter invoked by the runnable changes. It will
	 * continue doing so until the returned {@link ISideEffect} is disposed. The
	 * returned {@link ISideEffect} is associated with the default realm. The
	 * caller must dispose the returned {@link ISideEffect} when they are done
	 * with it.
	 *
	 * @param runnable
	 *            an idempotent runnable which will be executed once
	 *            synchronously then additional times after any tracked getter
	 *            it uses changes state
	 * @return an {@link ISideEffect} interface that may be used to stop the
	 *         side-effect from running. The {@link Runnable} will not be
	 *         executed anymore after the dispose method is invoked.
	 */
	static ISideEffect create(Runnable runnable) {
		IObservable[] dependencies = ObservableTracker.runAndMonitor(runnable, null, null);

		if (dependencies.length == 0) {
			return SideEffect.NULL_SIDE_EFFECT;
		}

		return new SideEffect(runnable, dependencies);
	}

	/**
	 * Runs the supplier and passes its result to the consumer. The same thing
	 * will happen again after any tracked getter invoked by the supplier
	 * changes. It will continue to do so until the given {@link ISideEffect} is
	 * disposed. The returned {@link ISideEffect} is associated with the default
	 * realm. The caller must dispose the returned {@link ISideEffect} when they
	 * are done with it.
	 * <p>
	 * The ISideEffect will initially be in the resumed state.
	 * <p>
	 * The first invocation of this method will be synchronous. This version is
	 * slightly more efficient than {@link #createResumed(Supplier, Consumer)} and
	 * should be preferred if synchronous execution is acceptable.
	 *
	 * @param supplier
	 *            a supplier which will compute a value and be monitored for
	 *            changes in tracked getters. It should be side-effect-free.
	 * @param consumer
	 *            a consumer which will receive the value. It should be
	 *            idempotent. It will not be monitored for tracked getters.
