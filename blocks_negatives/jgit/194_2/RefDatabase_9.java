	synchronized void clearCache() {
		looseRefs = new HashMap<String, Ref>();
		looseRefsMTime = new HashMap<String, Long>();
		packedRefs = new HashMap<String, Ref>();
		looseSymRefs = new HashMap<String, String>();
		packedRefsLastModified = 0;
		packedRefsLength = 0;
