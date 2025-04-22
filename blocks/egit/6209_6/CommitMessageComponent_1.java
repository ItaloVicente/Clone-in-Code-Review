	public static class CommitStatus implements IMessageProvider {

		private static final CommitStatus OK = new CommitStatus();

		private final String message;

		private final int type;

		private CommitStatus() {
			message = null;
			type = NONE;
		}

		private CommitStatus(String message, int type) {
			this.message = message;
			this.type = type;
		}

		public String getMessage() {
			return message;
		}

		public int getMessageType() {
			return type;
		}
	}


