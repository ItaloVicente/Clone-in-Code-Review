	private void readPushOptions() throws IOException {
		pushOptions = new ArrayList<>(4);
		for (;;) {
			String option = pckIn.readString();
			if (option == PacketLineIn.END) {
				break;
