		if (walker instanceof DepthWalk) {
			DepthWalk dw = (DepthWalk) walker;
			g = new DepthGenerator(w
		} else {
			g = new PendingGenerator(w

			if (boundary) {
				((PendingGenerator) g).canDispose = false;
			}
