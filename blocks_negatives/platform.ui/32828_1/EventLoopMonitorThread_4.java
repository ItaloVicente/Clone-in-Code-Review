		 * On transition between events or sleeping/wake up, we need to reset the delay tracking state
		 * and possibly publish a long delay message. Updating eventStartOrResumeTime causes the polling
		 * thread to reset its stack traces, so it should always be changed *after* the event is
		 * published. The indeterminacy of threading may cause the polling thread to see both changes or
		 * only the (first) publishEvent change, but the only difference is a small window where if an
		 * additional stack trace was scheduled to be sampled, a bogus stack trace sample will be
		 * appended to the end of the samples. Analysis code needs to be aware that the last sample may
		 * not be relevant to the issue which caused the freeze.
