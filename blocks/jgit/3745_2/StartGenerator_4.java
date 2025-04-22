		if (w instanceof DepthRevWalk || w instanceof DepthObjectWalk) {
			int depth;
			final RevFlag SHALLOW;
			final RevFlag BOUNDARY;
			if (w instanceof DepthRevWalk) {
				depth = ((DepthRevWalk)w).getDepth();
				SHALLOW = ((DepthRevWalk)w).SHALLOW;
				BOUNDARY = null;
			}
			else {
				depth = ((DepthObjectWalk)w).getDepth();
				SHALLOW = ((DepthObjectWalk)w).SHALLOW;
				BOUNDARY = ((DepthObjectWalk)w).BOUNDARY;
			}
			g = new DepthGenerator(w
