	static class QuickAccessSearchWorkspeceElement extends QuickAccessElement {

		private static final String SEARCH_IN_WORKSPACE_ID = "search.in.workspace"; //$NON-NLS-1$

		private static final String SEARCH_TEXT_IN_WORKSPACE_ACTION_ID = "org.eclipse.search.ui.performTextSearchWorkingSet"; //$NON-NLS-1$

		String searchText;

		public QuickAccessSearchWorkspeceElement(QuickAccessProvider provider) {
			super(provider);
		}

		@Override
		public String getLabel() {
			return NLS.bind(QuickAccessMessages.QuickAccessContents_SearchInWorkspaceLabel, searchText);
		}

		@Override
		public String getId() {
			return SEARCH_IN_WORKSPACE_ID;
		}

		@Override
		public void execute() {
			try {
				ECommandService cs = PlatformUI.getWorkbench().getService(ECommandService.class);
				IHandlerService hs = PlatformUI.getWorkbench().getService(IHandlerService.class);

				Command cmd = cs.getCommand(SEARCH_TEXT_IN_WORKSPACE_ACTION_ID);

				IEvaluationContext c = hs.createContextSnapshot(false);
				IStructuredSelection iss = new StructuredSelection(new String[]{searchText, null});
				c.addVariable(ISources.ACTIVE_CURRENT_SELECTION_NAME, iss);

				hs.executeCommandInContext(new ParameterizedCommand(cmd, null), null, c);

			} catch (Exception e) {
				throw new RuntimeException(String.format("% not found", SEARCH_TEXT_IN_WORKSPACE_ACTION_ID)); //$NON-NLS-1$
			}
		}

		@Override
		public ImageDescriptor getImageDescriptor() {
			return null;
		}

	}

