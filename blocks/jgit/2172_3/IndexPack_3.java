		UnresolvedDelta delta = visit.nextChild;
		if (delta != null)
			return newVisit(delta

		if (p != null)
			return newVisit(p.nextChild
		return null;
	}

	private static DeltaVisit newVisit(UnresolvedDelta delta
		p.nextChild = delta.next;
		return new DeltaVisit(delta
