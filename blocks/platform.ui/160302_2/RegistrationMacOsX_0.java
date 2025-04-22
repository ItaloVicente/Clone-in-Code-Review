	private String getLsRegisterOutput() throws Exception {
		if (lsRegisterOutput != null) {
			return lsRegisterOutput;
		}
		long beforeCallLsRegister = System.currentTimeMillis();
		lsRegisterOutput = processExecutor.execute(LSREGISTER, DUMP);
		long afterCallLsRegister = System.currentTimeMillis();
		System.out.println("Call to lsregister took :" + (afterCallLsRegister - beforeCallLsRegister)); //$NON-NLS-1$
		return lsRegisterOutput;
	}

