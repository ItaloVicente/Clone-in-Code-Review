	private void encode() {
		byte[] buffer = new byte[HEADER_LENGTH + name.length + flags.length +
                 vbucketlist.length + backfilldate.length + value.length];


		for (int i = 0; i < flags.length; totalbody++, extralength++, i++) {
			buffer[HEADER_LENGTH + totalbody] = flags[i];
		}

		for (int i = 0; i < name.length; totalbody++, i++) {
			buffer[HEADER_LENGTH + totalbody] = name[i];
		}

		if (TapFlag.BACKFILL.hasFlag(getFlags())) {
			for (int i = 0; i < backfilldate.length; totalbody++, i++) {
				buffer[HEADER_LENGTH + totalbody] = backfilldate[i];
