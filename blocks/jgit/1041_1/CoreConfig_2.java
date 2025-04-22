
		long maxMem = Runtime.getRuntime().maxMemory();
		long sft = rc.getLong("core"
		sft = Math.min(sft
		sft = Math.min(sft
		streamFileThreshold = (int) sft;
