	}

	private static class HiddenResourceTypedElement
			extends LocalResourceTypedElement {

		private final IFile realFile;

		public HiddenResourceTypedElement(IFile file, IFile realFile) {
			super(file);
			this.realFile = realFile;
		}
