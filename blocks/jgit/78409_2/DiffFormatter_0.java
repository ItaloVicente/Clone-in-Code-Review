	public void setReader(ObjectReader reader
		setReader(reader
	}

	private void setReader(ObjectReader reader
		close();
		this.closeReader = closeReader;
		this.reader = reader;
		this.diffCfg = cfg.get(DiffConfig.KEY);
