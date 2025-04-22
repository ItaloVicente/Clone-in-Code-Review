	private static class TypedData {
		static final TypedData UNKNOWN = new TypedData(Constants.OBJ_BAD

		final int type;
		final byte[] val;

		public TypedData(int type
			this.type = type;
			this.val = val;
		}

		public TypedData applyDelta(byte[] deltaBytes) {
			return new TypedData(type
		}

		public byte[] digestUsing(MessageDigest objectDigest) {
			objectDigest.update(Constants.encodedTypeString(type));
			objectDigest.update((byte) ' ');
			objectDigest.update(Constants.encodeASCII(val.length));
			objectDigest.update((byte) 0);
			objectDigest.update(val);
			return objectDigest.digest();
		}
	}

	private static class DeltaVisit {

		final DeltaIdentifier id;
		final TypedData data;
		final PackedObjectInfo oe;
		final UnresolvedDelta a

		public DeltaVisit(PackedObjectInfo oe) {
			this.id = new DeltaIdentifier(oe.getOffset()
			this.data = TypedData.UNKNOWN;
			this.oe = oe;
			a = b = null;
		}

		public DeltaVisit(DeltaIdentifier id
						  UnresolvedDelta a
			this.id = id;
			this.data = data;
			this.oe = null;
			this.a = a;
			this.b = b;
		}
	}

