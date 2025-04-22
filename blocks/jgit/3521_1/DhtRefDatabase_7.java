
	static interface DhtRef extends Ref {
		RefData getRefData();
	}

	private static class DhtSymbolicRef extends SymbolicRef implements DhtRef {
		private final RefData data;

		DhtSymbolicRef(String refName
			super(refName
			this.data = data;
		}

		DhtSymbolicRef(String refName
			super(refName
			this.data = data;
		}

		public RefData getRefData() {
			return data;
		}
	}

	private static class DhtObjectIdRef implements DhtRef {
		private final String name;
		private final RefData data;
		private final ObjectId objectId;
		private final ObjectId peeledId;

		DhtObjectIdRef(String name
			this.name = name;
			this.data = data;
			this.objectId = data.hasTarget() ? idFrom(data.getTarget()) : null;
			this.peeledId = data.hasPeeled() ? idFrom(data.getPeeled()) : null;
		}

		public String getName() {
			return name;
		}

		public boolean isSymbolic() {
			return false;
		}

		public Ref getLeaf() {
			return this;
		}

		public Ref getTarget() {
			return this;
		}

		public ObjectId getObjectId() {
			return objectId;
		}

		public Ref.Storage getStorage() {
			return data.hasTarget() ? LOOSE : NEW;
		}

		public boolean isPeeled() {
			return data.getIsPeeled();
		}

		public ObjectId getPeeledObjectId() {
			return peeledId;
		}

		public RefData getRefData() {
			return data;
		}
	}
