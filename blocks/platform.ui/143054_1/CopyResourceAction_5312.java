	CopyResourceAction(IShellProvider provider, String name){
		super(name);
		Assert.isNotNull(provider);
		shellProvider = provider;
		initAction();
	}
