	private void readPushOptions() throws IOException {
		String pushOption = pckIn.readString();

		while (pushOption != PacketLineIn.END) {
			pushOptions.add(pushOption);
			pushOption = pckIn.readString();
		}

		System.out.println("ReceivePack: db = " + db);

		db.addPushOptions(pushOptions);
	}

