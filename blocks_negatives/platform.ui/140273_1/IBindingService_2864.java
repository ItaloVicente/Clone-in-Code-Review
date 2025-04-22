	 * Returns the current state of the key binding buffer. This will contain
	 * all of the keys currently awaiting processing. If the system is currently
	 * executing a command (as a result of a key press), then this will contain
	 * the trigger sequence used to execute the command. If the key binding
	 * architecture has seen part of multi-key binding, then this will contain
	 * the part that it has seen. Otherwise, this will return nothing.
