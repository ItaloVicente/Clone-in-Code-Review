		RawText rawText = new RawText(reader.open(blobId).getBytes());
		String s = rawText.getString(0, rawText.size(), false);
		final String[] lines = s.split("\n");
		for (final String l : lines) {
			out.print("    ");
			out.println(l);
