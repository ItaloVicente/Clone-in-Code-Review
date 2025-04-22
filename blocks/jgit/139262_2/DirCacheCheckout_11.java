	public static void checkoutToTempFile(Repository repo
			CheckoutMetadata checkoutMetadata
			WorkingTreeOptions opt
			throws MissingObjectException
		EolStreamType nonNullEolStreamType;
		if (checkoutMetadata.eolStreamType != null) {
			nonNullEolStreamType = checkoutMetadata.eolStreamType;
		} else if (opt.getAutoCRLF() == AutoCRLF.TRUE) {
			nonNullEolStreamType = EolStreamType.AUTO_CRLF;
		} else {
			nonNullEolStreamType = EolStreamType.DIRECT;
		}
		try (OutputStream channel = EolStreamTypeUtil.wrapOutputStream(
				new FileOutputStream(tmpFile)
			if (checkoutMetadata.smudgeFilterCommand != null) {
				if (FilterCommandRegistry
						.isRegistered(checkoutMetadata.smudgeFilterCommand)) {
					runBuiltinFilterCommand(repo
							channel);
				} else {
					runExternalFilterCommand(repo
							checkoutMetadata
							fs
				}
			} else {
				ol.copyTo(channel);
			}
		}
	}

