		DirCacheEntry newEntry = new DirCacheEntry(e.getRawPath()
				e.getStage());
		newEntry.setFileMode(e.getFileMode());
		newEntry.setObjectId(e.getObjectId());
		newEntry.setLastModified(e.getLastModifiedInstant());
		newEntry.setLength(e.getLength());
		builder.add(newEntry);
		return newEntry;
	}

	protected void addCheckoutMetadata(Map<String
			String path
			throws IOException {
		if (map != null) {
			EolStreamType eol = EolStreamTypeUtil.detectStreamType(
					OperationType.CHECKOUT_OP
					attributes);
			CheckoutMetadata data = new CheckoutMetadata(eol
					tw.getSmudgeCommand(attributes));
			map.put(path
		}
