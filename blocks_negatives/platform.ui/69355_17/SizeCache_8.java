	private Point controlComputeSize(int widthHint, int heightHint) {
		Point result = control.computeSize(widthHint, heightHint, flushChildren);
		flushChildren = false;

		return result;
	}

