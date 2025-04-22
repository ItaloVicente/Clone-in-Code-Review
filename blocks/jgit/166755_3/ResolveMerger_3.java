	private boolean isCrLfText(ObjectId blobId) {
		try {
			ObjectLoader loader = tw.getObjectReader().open(blobId
					Constants.OBJ_BLOB);
			try {
				return RawText.isCrLfText(loader.getCachedBytes());
			} catch (LargeObjectException e) {
				try (InputStream in = loader.openStream()) {
					return RawText.isCrLfText(in);
				}
			}
		} catch (IOException e) {
		}
		return false;
	}

