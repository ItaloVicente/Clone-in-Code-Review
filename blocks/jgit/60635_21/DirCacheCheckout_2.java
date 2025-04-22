
	public static class CheckoutMetadata {
		public final EolStreamType eolStreamType;

		public final String smudgeFilterCommand;

		public CheckoutMetadata(EolStreamType eolStreamType
				String smudgeFilterCommand) {
			this.eolStreamType = eolStreamType;
			this.smudgeFilterCommand = smudgeFilterCommand;
		}

		static CheckoutMetadata EMPTY = new CheckoutMetadata(
				EolStreamType.DIRECT
	}

