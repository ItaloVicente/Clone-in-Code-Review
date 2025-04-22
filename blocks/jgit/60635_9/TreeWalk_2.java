	@Override
	public EolStreamType getEolStreamType() {
		if (eolStreamTypeDetector == null) {
			eolStreamTypeDetector = new EolStreamTypeDetector(
					config.get(WorkingTreeOptions.KEY)
		}
		return eolStreamTypeDetector.getStreamType();
	}

