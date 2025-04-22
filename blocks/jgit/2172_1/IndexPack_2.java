	private DeltaVisit firstChildDeltaOf(PackedObjectInfo oe
		UnresolvedDelta a = reverse(removeBaseById(oe));
		UnresolvedDelta b = reverse(baseByPos.remove(oe.getOffset()));
		return nextDeltaFor(a
	}

	private DeltaVisit nextDeltaFor(UnresolvedDelta a
									TypedData td) {
		if (a != null) {
			if (b != null && b.id.pos > a.id.pos) {
				return new DeltaVisit(b.id
			} else {
				return new DeltaVisit(a.id
			}
		} else if (b != null) {
			return new DeltaVisit(b.id
