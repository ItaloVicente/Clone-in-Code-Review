	 * within the job runnable for the purpose of parenting dialogs. Care should
	 * be taken not to open dialogs gratuitously and only if user input is
	 * required for cases where the save cannot otherwise proceed - note that in
	 * any given save operation, many saveable objects may be saved at the same
	 * time. In particular, errors should be signaled by throwing an exception,
	 * or if an error occurs while running the job runnable, an error status
	 * should be returned.
