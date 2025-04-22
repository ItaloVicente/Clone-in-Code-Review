			if (!inCore) {
				if (!builder.commit()) {
					cleanUp();
					throw new IndexWriteException();
				}
				builder = null;

				checkout();
			} else {
				builder.finish();
				builder = null;
