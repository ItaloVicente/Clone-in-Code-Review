	public static final ActionFactory SHOW_WORKBOOK_EDITORS_PREVIOUS = new ActionFactory(
			"showWorkBookEditorsPrevious") {//$NON-NLS-1$

		@Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
			if (window == null) {
				throw new IllegalArgumentException();
			}

			WorkbenchCommandAction action = new WorkbenchCommandAction(
					"org.eclipse.ui.window.openEditorDropDownPrevious", //$NON-NLS-1$
					window);
			action.setId(getId());
			action.setText(WorkbenchMessages.WorkbookEditorsActionPrevious_label);

			return action;
		}
	};

