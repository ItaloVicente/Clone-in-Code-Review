				&& walker.hasRevSort(RevSort.TOPO_KEEP_BRANCH_TOGETHER)) {
			throw new IllegalStateException(JGitText
					.get().cannotCombineTopoSortWithTopoKeepBranchTogetherSort);
		}

		if (walker.hasRevSort(RevSort.TOPO)
				&& (g.outputType() & SORT_TOPO) == 0) {
