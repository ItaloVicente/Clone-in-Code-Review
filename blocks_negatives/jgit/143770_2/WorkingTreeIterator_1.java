			try (ObjectReader reader = repository.newObjectReader()) {
				ObjectLoader loader = reader.open(blobId, Constants.OBJ_BLOB);
				try {
					return RawText.isCrLfText(loader.getCachedBytes());
				} catch (LargeObjectException e) {
					try (InputStream in = loader.openStream()) {
						return RawText.isCrLfText(in);
