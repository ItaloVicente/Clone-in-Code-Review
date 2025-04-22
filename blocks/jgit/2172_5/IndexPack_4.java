	private static class DeltaVisit {
		final UnresolvedDelta delta;

		byte[] data;

		DeltaVisit parent;

		UnresolvedDelta nextChild;

		DeltaVisit() {
		}

		DeltaVisit(DeltaVisit parent) {
			this.parent = parent;
			this.delta = parent.nextChild;
			parent.nextChild = delta.next;
		}

		DeltaVisit next() {
			if (parent != null && parent.nextChild == null) {
				parent.data = null;
				parent = parent.parent;
			}

			if (nextChild != null)
				return new DeltaVisit(this);

			if (parent != null)
				return new DeltaVisit(parent);
			return null;
		}
	}

