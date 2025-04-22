	DeltaTask(PackConfig config, ObjectReader reader, DeltaCache dc,
			ThreadSafeProgressMonitor pm, int batchSize, int start,
			ObjectToPack[] list) {
		this.config = config;
		this.templateReader = reader;
		this.dc = dc;
		this.pm = pm;
		this.batchSize = batchSize;
		this.start = start;
		this.list = list;
