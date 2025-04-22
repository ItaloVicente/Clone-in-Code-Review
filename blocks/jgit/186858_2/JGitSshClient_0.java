	@Override
	public SshAgentFactory getAgentFactory() {
		return agentFactorySupplier.get();
	}

	@Override
	protected void checkConfig() {
		super.checkConfig();
		agentFactorySupplier = super::getAgentFactory;
	}

