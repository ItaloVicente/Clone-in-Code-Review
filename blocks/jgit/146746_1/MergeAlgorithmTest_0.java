	private String mergeWithBase(String commonBase
			throws IOException {
		MergeResult r = new MergeAlgorithm().merge(RawTextComparator.DEFAULT
				T(commonBase)
		ByteArrayOutputStream bo = new ByteArrayOutputStream(50);
		fmt.formatWithBaseMerge(bo
		return new String(bo.toByteArray()
	}

