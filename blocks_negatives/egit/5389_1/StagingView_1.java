	private void addNonResourceActions(IMenuManager menuMgr,
			final IStructuredSelection selection) {
		menuMgr.add(new Action(UIText.StagingView_replaceWithFileInGitIndex) {
			@Override
			public void run() {
				boolean performAction = MessageDialog.openConfirm(form.getShell(),
						UIText.DiscardChangesAction_confirmActionTitle,
						UIText.DiscardChangesAction_confirmActionMessage);
				if (!performAction)
					return ;
				String[] files = getSelectedFiles(selection);
				replaceWithFileInGitIndex(files);
			}
		});
