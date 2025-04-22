		try {
			RawText rawText = new RawText(argWalk.getObjectReader()
					.open(blobId).getCachedBytes(Integer.MAX_VALUE));
			for (int i = 0; i < rawText.size(); i++) {
				out.print("    ");
				out.println(rawText.getString(i));
			}
		} catch (LargeObjectException e) {
			out.println(MessageFormat.format(
					CLIText.get().noteObjectTooLargeToPrint
