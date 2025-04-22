		GC gc = new GC((FileRepository) repo);
		gc.setPackConfig(pconfig);
		gc.setProgressMonitor(monitor);
		if (this.expire != null)
			gc.setExpire(expire);

