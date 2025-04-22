	public static class YesNoType extends CredentialItem {
		private boolean value;

		public YesNoType(String promptText) {
			super(promptText
		}

		@Override
		public void clear() {
			value = false;
		}

		public boolean getValue() {
			return value;
		}

		public void setValue(boolean newValue) {
			value = newValue;
		}
	}

	public static class InformationalMessage extends CredentialItem {
		public InformationalMessage(String messageText) {
			super(messageText
		}

		@Override
		public void clear() {
		}
	}

