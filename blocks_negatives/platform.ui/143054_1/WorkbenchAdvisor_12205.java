	 * This method is called when the code handling a UI event throws an
	 * exception. In a perfectly functioning application, this method would
	 * never be called. In practice, it comes into play when there are bugs in
	 * the code that trigger unchecked runtime exceptions. It is also activated
	 * when the system runs short of memory, etc. Fatal errors (ThreadDeath) are
	 * not passed on to this method, as there is nothing that could be done.
