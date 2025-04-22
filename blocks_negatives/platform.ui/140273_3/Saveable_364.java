	 * operation. Returns null if this saveable has been successfully saved, or
	 * a job runnable that needs to be run to complete the save in the
	 * background. This method is called in the UI thread. If this saveable
	 * supports saving in the background, it should do only minimal work.
	 * However, since the job runnable returned by this method (if any) will not
	 * run on the UI thread, this method should copy any state that can only be
	 * accessed from the UI thread so that the job runnable will be able to
	 * access it.
