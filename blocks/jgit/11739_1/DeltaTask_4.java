
	int remaining() {
		DeltaWindow d = dw;
		return d != null ? d.remaining() : 0;
	}

	Slice stealWork() {
		DeltaWindow d = dw;
		return d != null ? d.stealWork() : null;
	}
