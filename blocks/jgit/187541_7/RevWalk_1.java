
			int cutoff = Integer.MAX_VALUE;
			needle.parseInGraph(this);
			if (needle.generation < cutoff) {
				cutoff = needle.generation;
			}
