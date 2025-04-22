	private static class DeltaVisit {
		final UnresolvedDelta id;

		final PackedObjectInfo oe;

		DeltaVisit parent;

		byte[] data;

		UnresolvedDelta nextChild;

		DeltaVisit(UnresolvedDelta id
			this.oe = null;
			this.id = id;
			this.parent = parent;
		}

		DeltaVisit(PackedObjectInfo oe) {
			this.oe = oe;
			this.id = new UnresolvedDelta(oe.getOffset()
		}
	}

