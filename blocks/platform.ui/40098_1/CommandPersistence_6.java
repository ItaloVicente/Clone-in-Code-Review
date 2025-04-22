	private static IContextProvider COMMAND_PROVIDER = new IContextProvider() {

		@Override
		public IEclipseContext getContext() {
			return WorkbenchPlugin.getDefault().getWorkbenchContext();
		}
	};

