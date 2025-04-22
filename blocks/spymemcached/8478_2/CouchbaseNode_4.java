	public Collection<HttpOperation> destroyWriteQueue() {
		Collection<HttpOperation> rv=new ArrayList<HttpOperation>();
		writeQ.drainTo(rv);
		return rv;
	}

