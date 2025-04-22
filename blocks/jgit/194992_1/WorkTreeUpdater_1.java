		CheckoutMetadata checkoutMetadata = new CheckoutMetadata(
				workingTreeOptions != null &&
						workingTreeOptions.getAutoCRLF() == AutoCRLF.TRUE ? EolStreamType.AUTO_CRLF : streamType
				smudgeCommand);
		try (org.eclipse.jgit.util.TemporaryBuffer buffer =
				new org.eclipse.jgit.util.TemporaryBuffer.LocalFile(null)) {
			DirCacheCheckout.getContent(
					repo
			try (LfsInputStream is =
					org.eclipse.jgit.util.LfsFactory.getInstance()
							.applyCleanFilter(
									repo
									buffer.openInputStream()
									buffer.length()
									lfsAttribute)) {
				return inserter.insert(OBJ_BLOB
			}
