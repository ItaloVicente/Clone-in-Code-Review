
		/**
		 * Diff type
		 */
		public Type diffType = Type.OTHER;

		@Override
		public boolean similarTo(StyleRange style) {
			return super.similarTo(style) && style instanceof DiffStyleRange
					&& diffType == ((DiffStyleRange) style).diffType;
		}
