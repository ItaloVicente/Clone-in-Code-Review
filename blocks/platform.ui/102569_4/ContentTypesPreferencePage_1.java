		final String text;

		final int type;

		final boolean isPredefined;

		final int sortValue;

		public Spec(String specText, int specType, boolean isPredefined, int sortValue) {
			if (specType != IContentType.FILE_NAME_SPEC && specType != IContentType.FILE_EXTENSION_SPEC
					&& specType != IContentType.FILE_PATTERN_SPEC) {
				throw new IllegalArgumentException("Invalid specType"); //$NON-NLS-1$
			}
			this.type = specType;
			this.text = specText;
			this.isPredefined = isPredefined;
			this.sortValue = sortValue;
		}
