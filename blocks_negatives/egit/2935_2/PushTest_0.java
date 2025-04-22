	private Ref createRef(final String branch) {
		Ref ref = new Ref() {

			public String getName() {
				return branch;
			}

			public boolean isSymbolic() {
				return false;
			}

			public Ref getLeaf() {
				return null;
			}

			public Ref getTarget() {
				return null;
			}

			public ObjectId getObjectId() {
				return null;
			}

			public ObjectId getPeeledObjectId() {
				return null;
			}

			public boolean isPeeled() {
				return false;
			}

			public Storage getStorage() {
				return null;
			}

		};
		return ref;
	}

