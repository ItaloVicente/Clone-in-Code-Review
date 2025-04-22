
		long maxMem = Runtime.getRuntime().maxMemory();
		long sft = rc.getLong("core", null, "streamfilethreshold", STREAM_THRESHOLD);
		sft = Math.min(sft, maxMem / 4); // don't use more than 1/4 of the heap
		sft = Math.min(sft, Integer.MAX_VALUE); // cannot exceed array length
		streamFileThreshold = (int) sft;

