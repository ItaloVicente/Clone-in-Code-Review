		try (OutputStream channel = EolStreamTypeUtil.wrapOutputStream(
				new FileOutputStream(tmpFile)
			if (checkoutMetadata.smudgeFilterCommand != null) {
				if (FilterCommandRegistry
						.isRegistered(checkoutMetadata.smudgeFilterCommand)) {
					runBuiltinFilterCommand(repo
							channel);
				} else {
					runExternalFilterCommand(repo
							fs
