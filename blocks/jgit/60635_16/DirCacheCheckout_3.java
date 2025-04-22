		EolStreamType nonNullEolStreamType;
		if (checkoutMetadata.eolStreamType != null) {
			nonNullEolStreamType = checkoutMetadata.eolStreamType;
		} else if (opt.getAutoCRLF() == AutoCRLF.TRUE) {
			nonNullEolStreamType = EolStreamType.AUTO_CRLF;
		} else {
			nonNullEolStreamType = EolStreamType.DIRECT;
		}
		OutputStream channel = EolStreamTypeUtil.wrapOutputStream(
				new FileOutputStream(tmpFile)
		if (checkoutMetadata.smudgeFilterCommand != null) {
			ProcessBuilder filterProcessBuilder = fs.runInShell(
					checkoutMetadata.smudgeFilterCommand
