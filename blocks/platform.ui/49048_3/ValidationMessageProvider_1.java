	private String initalMessage;
	private int initalType;

	public ValidationMessageProvider() {
		initalMessage = null;
		initalType = IMessageProvider.NONE;
	}

	public ValidationMessageProvider(IMessageProvider messageProvider) {
		this(messageProvider.getMessage(), messageProvider.getMessageType());
	}

	public ValidationMessageProvider(String initalMessage, int initalType) {
		this.initalMessage = initalMessage;
		this.initalType = initalType;
	}

