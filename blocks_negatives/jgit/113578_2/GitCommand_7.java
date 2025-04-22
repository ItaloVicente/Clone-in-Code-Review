 * {@link #call()} on this instance. Instances of {@link GitCommand} can only be
 * used for one single successful call to {@link #call()}. Afterwards this
 * instance may not be used anymore to set/modify any properties or to call
 * {@link #call()} again. This is achieved by setting the {@link #callable}
 * property to false after the successful execution of {@link #call()} and to
 * check the state (by calling {@link #checkCallable()}) before setting of
 * properties and inside {@link #call()}.
