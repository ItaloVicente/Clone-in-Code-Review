	 * The getSizeFlags method controls how frequently this method will be
	 * called and what information will be available when it is. Any subclass
	 * that specializes this method should also specialize getSizeFlags.
	 * computePreferredSize(width, INFINITE, someSize, 0) returns the minimum
	 * size of the control (if any). computePreferredSize(width, INFINITE,
	 * someSize, INFINITE) returns the maximum size of the control.
