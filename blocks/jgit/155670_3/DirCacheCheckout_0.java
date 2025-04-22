	public static void checkoutToFile(Repository repo
			CheckoutMetadata checkoutMetadata
			WorkingTreeOptions opt
			throws IOException {
		EolStreamType nonNullEolStreamType;
		if (checkoutMetadata.eolStreamType != null) {
			nonNullEolStreamType = checkoutMetadata.eolStreamType;
		} else if (opt.getAutoCRLF() == AutoCRLF.TRUE) {
			nonNullEolStreamType = EolStreamType.AUTO_CRLF;
		} else {
			nonNullEolStreamType = EolStreamType.DIRECT;
		}
		try (OutputStream channel = EolStreamTypeUtil.wrapOutputStream(
				new FileOutputStream(file)
			if (checkoutMetadata.smudgeFilterCommand != null) {
				if (FilterCommandRegistry
						.isRegistered(checkoutMetadata.smudgeFilterCommand)) {
					runBuiltinFilterCommand(repo
							channel);
				} else {
					runExternalFilterCommand(repo
							channel);
				}
			} else {
				ol.copyTo(channel);
			}
		}
	}

