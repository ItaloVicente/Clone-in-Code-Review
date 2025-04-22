	/** {@inheritDoc} */
	@Override
	protected void enableCapabilities() {
		reportStatus = isCapabilityEnabled(CAPABILITY_REPORT_STATUS);
		usePushOptions = isCapabilityEnabled(CAPABILITY_PUSH_OPTIONS);
		super.enableCapabilities();
	}

	@Override
	void readPostCommands(PacketLineIn in) throws IOException {
		if (usePushOptions) {
			pushOptions = new ArrayList<>(4);
			for (;;) {
				String option = in.readString();
				if (PacketLineIn.isEnd(option)) {
					break;
				}
				pushOptions.add(option);
			}
		}
	}

