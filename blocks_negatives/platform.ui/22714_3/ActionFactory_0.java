        public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
			WorkbenchCommandAction action = new WorkbenchCommandAction(
					IWorkbenchCommandConstants.HELP_ABOUT, window);
            action.setId(getId());
            return action;
        }
