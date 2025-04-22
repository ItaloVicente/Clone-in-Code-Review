		do {
			UnresolvedDelta childId = visit.nextChild;
			if (childId != null) {
				visit.nextChild = childId.next;
				return new DeltaVisit(childId
			}
			visit = visit.parent;
		} while (visit != null);
		return null;
