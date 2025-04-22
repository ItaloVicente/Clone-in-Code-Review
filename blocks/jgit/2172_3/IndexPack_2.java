
			visit.nextChild = firstChildOf(oe);
			visit = nextVisit(visit);
		} while (visit != null);
	}

	private static DeltaVisit nextVisit(DeltaVisit visit) {
		DeltaVisit p = visit.parent;
		if (p != null && p.nextChild == null) {
			p.data = null;
			p = p.parent;
			visit.parent = p;
