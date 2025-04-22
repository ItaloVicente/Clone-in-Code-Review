		return getEolStreamType(opType
	}

	@Nullable
	public EolStreamType getEolStreamType(OperationType opType
			AnyObjectId objectId) {
		if (attributesNodeProvider == null || config == null)
			return null;
		OperationType op = opType != null ? opType : operationType;
		EolStreamType streamType = EolStreamTypeUtil.detectStreamType(op
				config.get(WorkingTreeOptions.KEY)
		if (streamType == EolStreamType.AUTO_LF
				&& isCommittedWithCrLf(objectId)) {
			return op == OperationType.CHECKIN_OP ? EolStreamType.DIRECT
					: EolStreamType.AUTO_CRLF;
		}
		return streamType;
	}

	@Nullable
	public EolStreamType getEolStreamType(OperationType opType
			DirCacheIterator index) {
