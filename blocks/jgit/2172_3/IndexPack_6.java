	private static class DeltaVisit {
		final UnresolvedDelta delta;

		byte[] data;

		DeltaVisit parent;

		UnresolvedDelta nextChild;

		DeltaVisit() {
		}

		DeltaVisit(UnresolvedDelta delta
			this.delta = delta;
			this.parent = parent;
		}
	}

