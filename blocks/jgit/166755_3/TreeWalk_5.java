		OperationType op = opType != null ? opType : operationType;
		EolStreamType streamType = EolStreamTypeUtil.detectStreamType(op
				config.get(WorkingTreeOptions.KEY)
		if (index != null && streamType == EolStreamType.AUTO_LF
				&& hasCrLf(findBlobId(index))) {
			return op == OperationType.CHECKIN_OP ? EolStreamType.DIRECT
					: EolStreamType.AUTO_CRLF;
		}
		return streamType;
	}

	private boolean isCommittedWithCrLf(AnyObjectId objectId) {
		AnyObjectId blobId = objectId;
		if (blobId == null) {
			DirCacheIterator index = getTree(DirCacheIterator.class);
			if (index != null) {
				blobId = findBlobId(index);
			}
			if (blobId == null) {
				CanonicalTreeParser ci = null;
				for (int i = trees.length - 1; i >= 0; i--) {
					if ((getRawMode(i)
							& FileMode.TYPE_MASK) == FileMode.TYPE_FILE) {
						if (trees[i] instanceof CanonicalTreeParser) {
							ci = (CanonicalTreeParser) trees[i];
							break;
						}
					}
				}
				if (ci != null) {
					blobId = ci.getEntryObjectId();
				}
			}
		}
		return hasCrLf(blobId);
	}

	private boolean hasCrLf(AnyObjectId blobId) {
		if (blobId != null) {
			try {
				ObjectLoader loader = reader.open(blobId
				try {
					return RawText.isCrLfText(loader.getCachedBytes());
				} catch (LargeObjectException e) {
					try (InputStream in = loader.openStream()) {
						return RawText.isCrLfText(in);
					}
				}
			} catch (IOException e) {
			}
		}
		return false;
	}

	private AnyObjectId findBlobId(@NonNull DirCacheIterator index) {
		AnyObjectId blobId = null;
		if (((AbstractTreeIterator) index).matches == currentHead) {
			DirCacheEntry entry = index.getDirCacheEntry();
			if ((entry.getRawMode()
					& FileMode.TYPE_MASK) == FileMode.TYPE_FILE) {
				blobId = entry.getObjectId();
				if (entry.getStage() > 0
						&& entry.getStage() != DirCacheEntry.STAGE_2) {
					blobId = null;
					byte[] name = entry.getRawPath();
					int i = 0;
					while (!index.eof()) {
						index.next(1);
						i++;
						entry = index.getDirCacheEntry();
						if (entry == null
								|| !Arrays.equals(name
							break;
						}
						if (entry.getStage() == DirCacheEntry.STAGE_2) {
							if ((entry.getRawMode()
									& FileMode.TYPE_MASK) == FileMode.TYPE_FILE) {
								blobId = entry.getObjectId();
							}
							break;
						}
					}
					index.back(i);
				}
			}
		}
		return blobId;
