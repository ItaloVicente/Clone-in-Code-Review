
		walker.queue = q;
		g = new PendingGenerator(w, pending, rf, pendingOutputType);

		if (boundary) {
			((PendingGenerator) g).canDispose = false;
		}

		if ((g.outputType() & NEEDS_REWRITE) != 0) {
			g = new FIFORevQueue(g);
			g = new RewriteGenerator(g);
		}

		if (walker.hasRevSort(RevSort.TOPO)
				&& (g.outputType() & SORT_TOPO) == 0)
			g = new TopoSortGenerator(g);
		if (walker.hasRevSort(RevSort.REVERSE))
			g = new LIFORevQueue(g);
		if (boundary)
			g = new BoundaryGenerator(w, g);
		else if (uninteresting) {
			if (pending.peek() != null)
				g = new DelayRevQueue(g);
			g = new FixUninterestingGenerator(g);
