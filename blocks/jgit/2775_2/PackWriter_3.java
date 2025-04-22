		public static class ObjectType {
			long cntObjects;

			long cntDeltas;

			long reusedObjects;

			long reusedDeltas;

			long bytes;

			long deltaBytes;

			public long getObjects() {
				return cntObjects;
			}

			public long getDeltas() {
				return cntDeltas;
			}

			public long getReusedObjects() {
				return reusedObjects;
			}

			public long getReusedDeltas() {
				return reusedDeltas;
			}

			public long getBytes() {
				return bytes;
			}

			public long getDeltaBytes() {
				return deltaBytes;
			}
		}

