		if (walker.hasRevSort(RevSort.TOPO)) {
			if (walker.hasRevSort(RevSort.TOPO_NON_INTERMIX)) {
				throw new IllegalStateException(MessageFormat.format(
						JGitText.get().cannotCombineTopoSortWithTopoNonIntermixSort
						tf
						rf));
			}
			if ((g.outputType() & SORT_TOPO) == 0) {
				g = new TopoSortGenerator(g);
			}
		} else if (walker.hasRevSort(RevSort.TOPO_NON_INTERMIX)
				&& (g.outputType() & SORT_TOPO) == 0) {
			g = new TopoNonIntermixSortGenerator(g);
		}
