
		PartLifeCycleState.transitionPartStateIf(PartLifeCycleState.HIDDEN,
				part, PartLifeCycleState.VISIBLE);
		PartLifeCycleState.transitionPartStateIf(PartLifeCycleState.RENDERED,
				part, PartLifeCycleState.VISIBLE);

