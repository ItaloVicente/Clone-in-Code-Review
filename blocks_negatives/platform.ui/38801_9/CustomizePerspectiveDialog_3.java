		IWorkbenchWindowConfigurer configurer;

		/**
		 * Fake action bars to use to build the menus and toolbar contributions
		 * for the workbench. We cannot use the actual workbench action bars,
		 * since doing so would make the action set items visible.
		 */
		private MenuManager menuManager = new MenuManager();
		private CoolBarManager coolBarManager = new CoolBarManager();
		private StatusLineManager statusLineManager = new StatusLineManager();

		/**
		 * Create a new instance of this class.
		 * 
		 * @param configurer
		 *            the configurer
		 */
		public CustomizeActionBars(IWorkbenchWindowConfigurer configurer) {
			this.configurer = configurer;
		}

		@Override
		public IWorkbenchWindowConfigurer getWindowConfigurer() {
			return configurer;
		}

		@Override
		public IMenuManager getMenuManager() {
			return menuManager;
		}

		@Override
		public IStatusLineManager getStatusLineManager() {
			return statusLineManager;
		}

		@Override
		public ICoolBarManager getCoolBarManager() {
			return coolBarManager;
		}

		@Override
		public IToolBarManager getToolBarManager() {
			return null;
		}

		@Override
		public void setGlobalActionHandler(String actionID, IAction handler) {
		}

		@Override
		public void updateActionBars() {
		}

		@Override
		public void clearGlobalActionHandlers() {
		}

		@Override
		public IAction getGlobalActionHandler(String actionId) {
			return null;
		}

		@Override
		public void registerGlobalAction(IAction action) {
		}

		/**
		 * Clean up the action bars.
		 */
		public void dispose() {
			coolBarManager.dispose();
			menuManager.dispose();
			statusLineManager.dispose();
		}

		@Override
		public final IServiceLocator getServiceLocator() {
			return configurer.getWindow();
		}

		@Override
		public IToolBarContributionItem createToolBarContributionItem(
				IToolBarManager toolBarManager, String id) {
			return new ToolBarContributionItem2(toolBarManager, id);
		}

		@Override
		public IToolBarManager createToolBarManager() {
			return new ToolBarManager();
