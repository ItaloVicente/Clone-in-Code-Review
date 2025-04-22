	private String getLsRegisterOutput() throws Exception {
		if (this.lsRegisterOutput != null) {
			return this.lsRegisterOutput;
		}
		this.lsRegisterOutput = processExecutor.execute(LSREGISTER, DUMP);
		return this.lsRegisterOutput;
	}

