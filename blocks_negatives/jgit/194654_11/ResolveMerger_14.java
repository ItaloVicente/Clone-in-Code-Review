		if (!inCore) {
			checkout();

			if (!builder.commit()) {
				cleanUp();
				throw new IndexWriteException();
			}
			builder = null;

		} else {
			builder.finish();
			builder = null;
		}

