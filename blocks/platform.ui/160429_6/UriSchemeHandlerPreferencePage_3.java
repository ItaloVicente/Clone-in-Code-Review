
		public String getHandlerInstanceLocation() {
			return information.getHandlerInstanceLocation();
		}

		public String getName() {
			return information.getName();
		}

		public String getDescription() {
			return information.getDescription();
		}

		public boolean isChecked() {
			return checked;
		}
	}

	final static class LoadingSchemeInformation extends UiSchemeInformation {

		private IScheme scheme;

		public LoadingSchemeInformation(IScheme scheme ) {
			super(false, null);
			this.scheme = scheme;
		}

		@Override
		public String getName() {
			return scheme.getName();
		}

		@Override
		public String getDescription() {
			return scheme.getDescription();
		}

		@Override
		public String getHandlerInstanceLocation() {
			return UrlHandlerPreferencePage_LoadingText;
		}

		@Override
		public boolean isChecked() {
			return false;
		}

