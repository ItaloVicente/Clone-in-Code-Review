	void updateDeltaDepth() {
		final int d;
		if (deltaBase instanceof ObjectToPack)
			d = ((ObjectToPack) deltaBase).getDeltaDepth() + 1;
		else if (deltaBase != null)
			d = 1;
		else
			d = 0;
