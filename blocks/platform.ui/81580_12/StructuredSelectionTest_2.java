	static final IElementComparer JAVA_LANG_OBJECT_COMPARER = new IElementComparer() {
		@Override
		public int hashCode(Object element) {
			return element.hashCode() + 71; // arbitrary prime number different from the
		}

		@Override
		public boolean equals(Object a, Object b) {
			return a.equals(b);
		}
	};

	static final IElementComparer IDENTITY_COMPARER = new IElementComparer() {
		@Override
		public int hashCode(Object element) {
			return element.hashCode() + 97; // arbitrary prime number different from the
		}

		@Override
		public boolean equals(Object a, Object b) {
			return a == b;
		}
	};

	public StructuredSelectionTest(String name) {
