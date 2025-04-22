	private IContextProvider provider;

	public CommandToModelProcessor() {
		this.provider = new IContextProvider() {

			@Override
			public IEclipseContext getContext() {
				return WorkbenchPlugin.getDefault().getWorkbenchContext();
			}
		};
	}

