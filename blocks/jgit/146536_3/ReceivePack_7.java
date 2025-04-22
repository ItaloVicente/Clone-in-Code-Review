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

	protected void enableCapabilities() {
		reportStatus = isCapabilityEnabled(CAPABILITY_REPORT_STATUS);
		usePushOptions = isCapabilityEnabled(CAPABILITY_PUSH_OPTIONS);
		sideBand = isCapabilityEnabled(CAPABILITY_SIDE_BAND_64K);
		quiet = allowQuiet && isCapabilityEnabled(CAPABILITY_QUIET);
		if (sideBand) {
			OutputStream out = rawOut;

			rawOut = new SideBandOutputStream(CH_DATA
			msgOut = new SideBandOutputStream(CH_PROGRESS
			errOut = new SideBandOutputStream(CH_ERROR

			pckOut = new PacketLineOut(rawOut);
			pckOut.setFlushOnEnd(false);
		}
	}

