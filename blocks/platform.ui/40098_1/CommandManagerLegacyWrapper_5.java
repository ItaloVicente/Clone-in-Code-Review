	private IContextProvider commandProvider = new IContextProvider() {

		@Override
		public IEclipseContext getContext() {
			return WorkbenchPlugin.getDefault().getWorkbenchContext();
		}
	};

