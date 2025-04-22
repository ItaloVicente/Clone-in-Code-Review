		}

		if (!inCore) {
			checkout();

			if (!builder.commit()) {
				cleanUp();
				throw new IndexWriteException();
