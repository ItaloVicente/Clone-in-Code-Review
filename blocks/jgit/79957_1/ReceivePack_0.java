	@Override
	void readPostCommands(PacketLineIn in) throws IOException {
		if (usePushOptions) {
			pushOptions = new ArrayList<>(4);
			for (;;) {
				String option = in.readString();
				if (option == PacketLineIn.END) {
					break;
				}
				pushOptions.add(option);
