		if (w instanceof DepthRevWalk || w instanceof DepthObjectWalk) {
			int depth;
			final RevFlag SHALLOW;
			final RevFlag BOUNDARY;
			if (w instanceof DepthRevWalk) {
				depth = ((DepthRevWalk)w).getDepth();
				SHALLOW = ((DepthRevWalk)w).SHALLOW;
				BOUNDARY = null;
			} else {
				depth = ((DepthObjectWalk)w).getDepth();
				SHALLOW = ((DepthObjectWalk)w).SHALLOW;
				BOUNDARY = ((DepthObjectWalk)w).BOUNDARY;
			}
			g = new DepthGenerator(w
		} else {
			if (tf != TreeFilter.ALL) {
				rf = AndRevFilter.create(rf
				pendingOutputType |= HAS_REWRITE | NEEDS_REWRITE;
			}

			walker.queue = q;
			g = new PendingGenerator(w

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
				g = new BoundaryGenerator(w
			else if (uninteresting) {
				if (pending.peek() != null)
					g = new DelayRevQueue(g);
				g = new FixUninterestingGenerator(g);
			}
