	public static final ActionFactory HELP_CONTENTS = new ActionFactory("helpContents", //$NON-NLS-1$
			IWorkbenchCommandConstants.HELP_HELP_CONTENTS) {

		@Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
			if (window == null) {
				throw new IllegalArgumentException();
			}
			IWorkbenchAction action = new HelpContentsAction(window);
			action.setId(getId());
			return action;
		}
	};

