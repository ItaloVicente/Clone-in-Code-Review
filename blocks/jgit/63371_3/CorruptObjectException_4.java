	private ObjectChecker.ErrorType errorType;

	public CorruptObjectException(ObjectChecker.ErrorType type
			String why) {
		super(MessageFormat.format(JGitText.get().objectIsCorrupt3
				type.getMessageId()
		this.errorType = type;
	}

