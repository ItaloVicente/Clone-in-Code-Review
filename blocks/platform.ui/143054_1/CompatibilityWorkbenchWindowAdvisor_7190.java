	private WorkbenchAdvisor wbAdvisor;

	public CompatibilityWorkbenchWindowAdvisor(WorkbenchAdvisor wbAdvisor,
			IWorkbenchWindowConfigurer windowConfigurer) {
		super(windowConfigurer);
		this.wbAdvisor = wbAdvisor;
	}

	@Override
