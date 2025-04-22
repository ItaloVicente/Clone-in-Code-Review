
	private PackObjectSizeIndex objSizeIdx() throws IOException {
		PackObjectSizeIndex sizeIdx = loadedObjSizeIdx;
		if (sizeIdx == null && !attemptLoadObjSizeIdx) {
			synchronized (this) {
				sizeIdx = loadedObjSizeIdx;
				if (sizeIdx == null) {
					try {
						long start = System.currentTimeMillis();
						PackFile sizeIdxFile = packFile
								.create(OBJECT_SIZE_INDEX);
						if (attemptLoadObjSizeIdx || !sizeIdxFile.exists()) {
							attemptLoadObjSizeIdx = true;
							return null;
						}
						sizeIdx = PackObjectSizeIndexLoader
								.load(new FileInputStream(
										sizeIdxFile.getAbsoluteFile()));
						if (LOG.isDebugEnabled()) {
							LOG.debug(String.format(
									"Opening obj size index %s
									sizeIdxFile.getAbsolutePath()
									Float.valueOf(
											sizeIdxFile.length()
													/ (1024f * 1024))
									Long.valueOf(System.currentTimeMillis()
											- start)));
						}

						loadedObjSizeIdx = sizeIdx;
					} catch (InterruptedIOException e) {
						return null;
					} finally {
						attemptLoadObjSizeIdx = true;
					}
				}
			}
		}
		return sizeIdx;
	}

