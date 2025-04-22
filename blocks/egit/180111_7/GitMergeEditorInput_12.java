
	private static class HiddenResourceTypedElement
			extends LocalResourceTypedElement {

		private final IFile realFile;

		public HiddenResourceTypedElement(IFile file, IFile realFile) {
			super(file);
			this.realFile = realFile;
		}

		public IFile getRealFile() {
			return realFile;
		}

		@Override
		public boolean equals(Object obj) {
			return super.equals(obj);
		}

		@Override
		public int hashCode() {
			return super.hashCode();
		}
	}
