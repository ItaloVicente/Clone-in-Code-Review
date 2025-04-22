	private final TapOpcode opcode;
	private final int opaque;

	TapAckOperationImpl(TapOpcode opcode, int opaque, OperationCallback cb) {
		super(cb);
		this.opcode = opcode;
		this.opaque = opaque;
	}

	@Override
	public void initialize() {
		RequestMessage message = new RequestMessage();
		message.setMagic(TapMagic.PROTOCOL_BINARY_RES);
		message.setOpcode(opcode);
		message.setOpaque(opaque);
		setBuffer(message.getBytes());
	}

	@Override
	public void readFromBuffer(ByteBuffer data) {
	}

	@Override
	public void streamClosed(OperationState state) {
		transitionState(state);
	}
