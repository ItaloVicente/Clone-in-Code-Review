    Object updateLock = new Object();

	class MutableBoolean {
		boolean value;
	}

	/*
	 * True when update job is scheduled or running. This is used to limit the
	 * update job to no more than once every 100 ms. See bug 258352 and 395645.
	 */
	MutableBoolean updateScheduled = new MutableBoolean();

