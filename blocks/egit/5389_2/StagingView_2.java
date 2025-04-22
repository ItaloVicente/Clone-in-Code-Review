		IStructuredSelection selection;
		private final boolean headRevision;

		ReplaceAction(String text, IStructuredSelection selection, boolean headRevision) {
			super(text);
			this.selection = selection;
			this.headRevision = headRevision;
		}

		@Override
		public void run() {
			boolean performAction = MessageDialog.openConfirm(form.getShell(),
					UIText.DiscardChangesAction_confirmActionTitle,
					UIText.DiscardChangesAction_confirmActionMessage);
			if (!performAction)
				return ;
			String[] files = getSelectedFiles(selection);
			replaceWith(files, headRevision);
		}
