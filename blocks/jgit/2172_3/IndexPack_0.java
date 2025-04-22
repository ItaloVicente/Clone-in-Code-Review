		UnresolvedDelta children = firstChildOf(oe);
		if (children == null)
			return;

		DeltaVisit visit = new DeltaVisit();
		visit.nextChild = children;
