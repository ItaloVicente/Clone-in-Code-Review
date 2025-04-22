	@Override
	public void initialize() {
		RequestMessage message = new RequestMessage();
		message.setMagic(TapMagic.PROTOCOL_BINARY_REQ);
		message.setOpcode(TapOpcode.REQUEST);
		message.setFlags(TapFlag.DUMP);
		message.setFlags(TapFlag.SUPPORT_ACK);
		if (id != null) {
			message.setName(id);
		} else {
			message.setName(UUID.randomUUID().toString());
		}
