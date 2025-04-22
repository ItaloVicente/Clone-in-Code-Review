		public Type diffType = Type.OTHER;

		public DiffRegion(int offset, int length) {
			super(offset, length);
		}

		public DiffRegion(int offset, int length, Type type) {
			super(offset, length);
			this.diffType = type;
		}

