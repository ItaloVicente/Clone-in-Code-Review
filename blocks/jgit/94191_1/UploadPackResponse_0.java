	private final List<String> shallowResponseBuffer = new ArrayList<>();
	private boolean receivedNegotiateRequest;
	private boolean shouldFlushShallowResponse;

	UploadPackResponse(InputStream rawIn
			boolean biDirectionalPipe) {
		this.rawIn = rawIn;
