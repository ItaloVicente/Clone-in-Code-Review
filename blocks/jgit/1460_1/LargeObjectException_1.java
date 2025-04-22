		return MessageFormat.format(JGitText.get().largeObjectException
				getObjectName());
	}

	public static class OutOfMemory extends LargeObjectException {
		private static final long serialVersionUID = 1L;

		public OutOfMemory(OutOfMemoryError cause) {
			initCause(cause);
		}

		@Override
		public String getMessage() {
			return MessageFormat.format(JGitText.get().largeObjectOutOfMemory
					getObjectName());
		}
	}

	public static class ExceedsByteArrayLimit extends LargeObjectException {
		private static final long serialVersionUID = 1L;

		@Override
		public String getMessage() {
			return MessageFormat
					.format(JGitText.get().largeObjectExceedsByteArray
							getObjectName());
		}
	}

	public static class ExceedsLimit extends LargeObjectException {
		private static final long serialVersionUID = 1L;

		private final long limit;

		private final long size;

		public ExceedsLimit(long limit
			this.limit = limit;
			this.size = size;
		}

		@Override
		public String getMessage() {
			return MessageFormat.format(JGitText.get().largeObjectExceedsLimit
					getObjectName()
		}
