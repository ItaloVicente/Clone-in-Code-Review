		try (OutputStream channel = EolStreamTypeUtil.wrapOutputStream(
				new FileOutputStream(tmpFile)
			if (checkoutMetadata.smudgeFilterCommand != null) {
				if (Repository
						.isRegistered(checkoutMetadata.smudgeFilterCommand)) {
					runBuiltinFilterCommand(repo
				} else {
					runExternalFilterCommand(repo
							fs
