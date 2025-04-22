		if (f != null) {
			TemporaryBuffer buffer = new TemporaryBuffer.LocalFile(null);
			try {
				CheckoutMetadata metadata = new CheckoutMetadata(streamType
						smudgeFilterCommand);

				try (TemporaryBuffer buf = buffer) {
					DirCacheCheckout.getContent(repo
							metadata
							buf);
